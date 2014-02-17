package org.ddelizia.vcrud.core.security.filter;

import org.apache.log4j.Logger;

/**
 * @author ddelizia
 * @since 16/02/14 11:46
 */
public class AuthorizationRequestContext {

    private static final Logger LOGGER = Logger.getLogger(AuthorizationRequestContext.class);

    /**
     * The relative url of the request which starts at the root of the requested resource
     */
    private final String requestUrl;

    /**
     * The Http method (POST, GET, DELETE, PUT)
     */
    private final String httpMethod;

    /**
     * An Iso8061 formatted date timestamp
     */
    private final String requestDateString;

    /**
     * Client generated unique nonce value
     */
    private final String nonceToken;

    /**
     * The AuthorizationToken which should be in a format that the appropriate AuthorizationService can understand
     */
    private final String authorizationToken;

    public AuthorizationRequestContext(String requestUrl, String httpMethod, String requestDateString, String nonceToken, String hashedToken) {
        this.requestUrl = requestUrl;
        this.httpMethod = httpMethod;
        this.requestDateString = requestDateString;
        this.nonceToken = nonceToken;
        this.authorizationToken = hashedToken;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public String getRequestDateString() {
        return requestDateString;
    }

    public String getNonceToken() {
        return nonceToken;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }


}
