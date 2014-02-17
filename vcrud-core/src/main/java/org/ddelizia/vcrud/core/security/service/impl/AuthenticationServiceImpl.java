package org.ddelizia.vcrud.core.security.service.impl;

import org.ddelizia.vcrud.commons.PasswordRequest;
import org.ddelizia.vcrud.commons.client.LostPasswordRequest;
import org.ddelizia.vcrud.core.basic.service.AbstractService;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.exception.AlreadyVerifiedException;
import org.ddelizia.vcrud.core.security.exception.AuthenticationException;
import org.ddelizia.vcrud.core.security.exception.TokenHasExpiredException;
import org.ddelizia.vcrud.core.security.exception.UserNotFoundException;
import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.security.repository.VerificationTokenRepository;
import org.ddelizia.vcrud.core.security.service.AuthenticationService;
import org.ddelizia.vcrud.core.security.service.CryptoService;
import org.ddelizia.vcrud.core.security.service.EmailAuthenticationServiceGateway;
import org.ddelizia.vcrud.core.security.service.VerificationTokenService;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.hibernate.jpamodelgen.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 06/02/14
 * Time: 12:17
 * To change this template use File | Settings | File Templates.
 */

@Service(AuthenticationService.DEFAULT_BEAN_NAME)
public class AuthenticationServiceImpl extends AbstractService implements AuthenticationService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private EmailAuthenticationServiceGateway emailAuthenticationServiceGateway;

    @Autowired
    private AppConfig appConfig;

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    @Autowired
    @Qualifier(VerificationTokenService.DEFAULT_BEAN_NAME)
    private VerificationTokenService verificationTokenService;

    @Autowired
    @Qualifier(CryptoService.DEFAULT_BEAN_NAME)
    private CryptoService cryptoService;

    @Override
    public VerificationToken sendEmailVerificationToken(String userId) {
        User user = ensureUserIsLoaded(userId);
        return sendEmailVerificationToken(user);
    }

    private VerificationToken sendEmailVerificationToken(User user) {
        VerificationToken token = new VerificationToken(user,
                VerificationToken.VerificationTokenType.emailVerification,
                appConfig.getProperty(AppConfig.TOKEN_EMAIL_VERIFICATION_DURATION, Integer.class, null));
        verificationTokenRepository.save(token);
        emailAuthenticationServiceGateway.sendVerificationToken(
                new EmailServiceTokenData(
                        user,
                        token,
                        appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
                ));
        return token;
    }

    @Override
    public VerificationToken sendEmailRegistrationToken(String userId) {
        User user = ensureUserIsLoaded(userId);
        VerificationToken token = new VerificationToken(user,
                VerificationToken.VerificationTokenType.emailRegistration,
                appConfig.getProperty(AppConfig.TOKEN_EMAIL_REGISTRATION_DURATION, Integer.class, null));

        verificationTokenRepository.save(token);

        emailAuthenticationServiceGateway.sendVerificationToken(
                new EmailServiceTokenData(
                        user,
                        token,
                        appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
                ));
        return token;
    }

    /**
     * generate token if user found otherwise do nothing
     *
     * @param lostPasswordRequest
     * @return  a token or null if user not found
     */
    @Override
    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest) {
        validate(lostPasswordRequest);
        VerificationToken token = null;
        User user = userRepository.findByEmail(lostPasswordRequest.getEmailAddress());
        if (user != null) {
            token = verificationTokenService.getActiveToken(VerificationToken.VerificationTokenType.lostPassword,user);
            if (token == null) {
                token = new VerificationToken(
                        user,
                        VerificationToken.VerificationTokenType.lostPassword,
                        appConfig.getProperty(AppConfig.TOKEN_LOST_PASSWORD_DURATION, Integer.class, null)
                );

                verificationTokenRepository.save(token);
            }
            emailAuthenticationServiceGateway.sendVerificationToken(
                    new EmailServiceTokenData(
                            user,
                            token,
                            appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
                    ));
        }

        return token;
    }

    @Override
    public VerificationToken verify(String base64EncodedToken) {
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified() || userService.isVerified(token.getUser())) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        userService.verifyUser(token.getUser());
        userRepository.save(token.getUser());
        return token;
    }

    @Override
    public VerificationToken generateEmailVerificationToken(String emailAddress) {
        Assert.notNull(emailAddress);
        User user = userRepository.findByEmail(emailAddress);
        if (user == null) {
            throw new UserNotFoundException();
        }
        if (userService.isVerified(user)) {
            throw new AlreadyVerifiedException();
        }
        //if token still active resend that
        VerificationToken token = verificationTokenService.getActiveToken(VerificationToken.VerificationTokenType.emailVerification, user);;
        if (token == null) {
            token = sendEmailVerificationToken(user);
        } else {
            emailAuthenticationServiceGateway.sendVerificationToken(
                    new EmailServiceTokenData(
                            user,
                            token,
                            appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
                    ));
        }
        return token;
    }

    @Override
    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest) {
        Assert.notNull(base64EncodedToken);
        validate(passwordRequest);
        VerificationToken token = loadToken(base64EncodedToken);
        if (token.isVerified()) {
            throw new AlreadyVerifiedException();
        }
        token.setVerified(true);
        User user = token.getUser();
        try {
            user.setPassword(cryptoService.hashPassword(passwordRequest.getPassword(), user));
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        //set user to verified if not already and authenticated role
        userService.verifyUser(user);
        if (userService.isAnonymousUser(user)){
            userService.assignGroupToUser(
                    AppConfig.USER_USERGROUP_AUTHENTICATED,
                    user.getId()
            );
        }
        userRepository.save(user);
        return token;
    }

    private VerificationToken loadToken(String base64EncodedToken) {
        Assert.notNull(base64EncodedToken);
        String rawToken = new String(Base64.decode(base64EncodedToken.getBytes()));
        VerificationToken token = verificationTokenRepository.findByToken(rawToken);
        if (token == null) {
            throw new TokenHasExpiredException();
        }
        if (token.hasExpired()) {
            throw new TokenHasExpiredException();
        }
        return token;
    }


    private User ensureUserIsLoaded(String userIdentifier) {
        User user = null;
        if (userIdentifier!=null) {
            user = userRepository.findOne(userIdentifier);
        }
        if (userIdentifier!=null){
            user = userRepository.findByEmail(userIdentifier);
        }
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    public void setEmailAuthenticationServiceGateway(EmailAuthenticationServiceGateway emailAuthenticationServiceGateway) {
        this.emailAuthenticationServiceGateway = emailAuthenticationServiceGateway;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setVerificationTokenService(VerificationTokenService verificationTokenService) {
        this.verificationTokenService = verificationTokenService;
    }

    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }
}
