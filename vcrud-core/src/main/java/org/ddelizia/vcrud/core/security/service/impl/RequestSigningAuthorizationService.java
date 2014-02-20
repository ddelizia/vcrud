package org.ddelizia.vcrud.core.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.security.filter.AuthorizationRequestContext;
import org.ddelizia.vcrud.core.security.service.converter.ConverterUser2ExternalUser;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.basic.service.AbstractService;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.exception.AuthorizationException;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.repository.SessionTokenRepository;
import org.ddelizia.vcrud.core.security.service.AuthorizationService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Any Resource that requires a role must have a header property of the following format:
 * <p/>
 * <code>
 * Authorization: <uuid of user>:<Signed Request>
 * </code>
 * <p/>
 * The signed request hash is comprised of the session token + : +  the relative url + , + the Http method + , + Date + , + nonce
 * This string is then Sha-256 encoded and then Base64 encoded
 * <p/>
 * An example:
 * <code>
 * Example:
 * 9fbc6f9a-af1b-4767-a492-c8462fd2a4d9:user/2e2ce9e8-798e-42b6-9326-fd2e56aef7aa/cards,POST,2012-06-30T12:00:00+01:00,34e321a7c4
 * <p/>
 * </code>
 * <p/>
 * This will be SHA-256 hashed and then Base64 encoded to produce:
 * <p/>
 * <code>
 * HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
 * </code>
 * <p/>
 * Authorization: 2e2ce9e8-798e-42b6-9326-fd2e56aef7aa:HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
 *
 * @author: Iain Porter
 */
public class RequestSigningAuthorizationService extends AbstractService implements AuthorizationService{

    private final static Logger LOGGER = Logger.getLogger(RequestSigningAuthorizationService.class);

    /**
     * If the nonce already exists in the cache the difference between its timestamp and the current time will be
     * greater than this value
     */
    private static final int NONCE_CHECK_TOLERANCE_IN_MILLIS = 20;

    /**
     * Maximum Number of Nonce values in the cache
     * The capacity will never be reached as long as the number of requests is below this value within the time range specified by
     * ApplicationConfig.getSessionDateOffsetInMinutes()
     */
    private static final int NONCE_CACHE_SIZE = 10000;

    /**
     * A an expiry cache that evicts nonce values after a configurable time period
     */
    private LoadingCache<String, Nonce> nonceCache;

    /**
     * The configuration for the application
     */
    @Autowired
    private AppConfig appConfig;

    /**
     * User service required for persisting user objects
     */
    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConverterUser2ExternalUser converterUser2ExternalUser;

    public RequestSigningAuthorizationService() {
    }

    public void initNonceCache() {
        nonceCache = CacheBuilder.newBuilder()
                .maximumSize(NONCE_CACHE_SIZE)
                .expireAfterWrite(appConfig.getProperty(AppConfig.SESSION_DATE_OFFSET_IN_MINUTES, Integer.class, null)
                        , TimeUnit.MINUTES)
                .build(
                        new CacheLoader<String, Nonce>() {
                            public Nonce load(String key) throws Exception {
                                return new Nonce(new DateTime(), key);
                            }
                        });

    }

    /**
     * If the request contains values in the AuthorizationRequestContext attempt to find and validate a user
     *
     * @param context
     * @return The request signature was valid and a user is returned or null if the context did not contain the information necessary
     * to load a user
     */
    @Override
    public ExternalUser authorize(AuthorizationRequestContext context) {

        ExternalUser externalUser = null;
        if (context.getAuthorizationToken() != null && context.getRequestDateString() != null && context.getNonceToken() != null) {
            String userId = null;
            String hashedToken = null;
            String[] token = context.getAuthorizationToken().split(":");
            if (token.length == 2) {
                userId = token[0];
                hashedToken = token[1];
                //make sure date and nonce is valid
                validateRequestDate(context.getRequestDateString());
                validateNonce(context.getNonceToken());

                User user = userRepository.findOne(userId);
                if (user != null) {
                    externalUser = converterUser2ExternalUser.convert(user);
                    if (!isAuthorized(user, context, hashedToken)) {
                        throw new AuthorizationException("Request rejected due to an authorization failure");
                    }
                }
            }
        }
        return externalUser;
    }

    /**
     * Authorize a hashed token against a request string
     * The hashed token will be comprised of:
     * the User's session token + the relative request Url + the Http Verb + the Date as ISO 8061 String + a nonce token generated by the client
     * <code>
     * Example:
     * 9fbc6f9a-af1b-4767-a492-c8462fd2a4d9:user/2e2ce9e8-798e-42b6-9326-fd2e56aef7aa,GET,2012-06-30T12:00:00+01:00,34e321a7c4
     * <p/>
     * </code>
     * <p/>
     * This will be SHA-256 hashed and then Base64 encoded to produce:
     * <p/>
     * <code>
     * HR/3DJp8RCGo50Wu+/3cr7ibdoNXKg1eYMt3HO5QoP4=
     * </code>
     *
     * @param user should have a session token that will validate the request signature
     * @param authorizationRequest the request containing all the details needed to authorize the request
     * @param hashedToken the token to match against
     * @return true if the token is authorized
     */
    private boolean isAuthorized(User user, AuthorizationRequestContext authorizationRequest, String hashedToken) {
        Assert.notNull(user);
        Assert.notNull(authorizationRequest.getAuthorizationToken());
        String unEncodedString = composeUnEncodedRequest(authorizationRequest);
        Set<SessionToken> sessionTokens = new HashSet<>(sessionTokenRepository.findByUser(user));
        String userTokenHash = null;
        for (SessionToken token : sessionTokens) {
            userTokenHash = encodeAuthToken(token.getToken(), unEncodedString);
            if (hashedToken.equals(userTokenHash)) {
                token.setLastModification(new DateTime(new Date()));
                sessionTokenRepository.save(token);
                return true;
            }
        }
        LOGGER.error("Hash check failed for hashed token: "+authorizationRequest.getAuthorizationToken()
                +" for the following request: "+unEncodedString
                +" for user: "+ user.getId());
        return false;
    }

    /**
     * Encode the token by prefixing it with the User's Session Token
     *
     * @param token
     * @return encoded token
     */
    private String encodeAuthToken(String token, String unencodedRequest) {
        byte[] digest = Sha512DigestUtils.sha(token + ":" + unencodedRequest);
        return new String(Base64.encode(digest));

    }

    /**
     * The recipe to compose a signed request
     *
     * @param authRequest
     * @return the string value to hash
     */
    private String composeUnEncodedRequest(AuthorizationRequestContext authRequest) {
        StringBuilder sb = new StringBuilder();
        sb.append(authRequest.getRequestUrl());
        sb.append(',');
        sb.append(authRequest.getHttpMethod().toUpperCase());
        sb.append(',');
        sb.append(authRequest.getRequestDateString());
        sb.append(',').append(authRequest.getNonceToken());
        return sb.toString();

    }

    /**
     * Ensure that the date of the request falls within the configured range
     * @param requestDateString
     */
    private void validateRequestDate(String requestDateString) {
        Date date = ISODateTimeFormat.dateTimeNoMillis().parseDateTime(requestDateString).toDate();
        DateTime now = new DateTime();
        DateTime offset = new DateTime(date);
        if (!(offset.isAfter(now.minusMinutes(
                appConfig.getProperty(AppConfig.SESSION_DATE_OFFSET_IN_MINUTES, Integer.class, null)
        )) &&
                offset.isBefore(now.plusMinutes(
                        appConfig.getProperty(AppConfig.SESSION_DATE_OFFSET_IN_MINUTES, Integer.class, null)
                )))) {
            LOGGER.error("Date in header is out of range: " + requestDateString);
            throw new AuthorizationException("Date in header is out of range: " + requestDateString);
        }
    }

    /**
     * The nonce value sent by the client and used in the request signature should be unique across the system
     * Nonce values will only be considered unique within the time limits of the cache.
     * The value will be protected if the cache expiry time is within the limits of the request date range.
     * If the date in the request is stale then the nonce value wil be irrelevant
     *
     * Note that the caching strategy will not work in a cluster. A distributed cache will be needed.
     *
     * @param nonceValue
     */
    private void validateNonce(String nonceValue) {
        Nonce nonce = nonceCache.getUnchecked(nonceValue);
        Duration tolerance = new Duration(nonce.timestamp, new DateTime());
        if (tolerance.isLongerThan(Duration.millis(NONCE_CHECK_TOLERANCE_IN_MILLIS))) {
            LOGGER.error("Nonce value was not unique: " + nonceValue);
            throw new AuthorizationException("Nonce value is not unique");
        }
    }

    private static class Nonce {
        private DateTime timestamp;
        private String nonceValue;

        Nonce(DateTime time, String nonce) {
            this.timestamp = time;
            this.nonceValue = nonce;
        }
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setSessionTokenRepository(SessionTokenRepository sessionTokenRepository) {
        this.sessionTokenRepository = sessionTokenRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setConverterUser2ExternalUser(ConverterUser2ExternalUser converterUser2ExternalUser) {
        this.converterUser2ExternalUser = converterUser2ExternalUser;
    }
}
