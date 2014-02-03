package org.ddelizia.vcrud.commons;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 16:48
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticatedUserToken {

    private String userId;
    private String token;

    public AuthenticatedUserToken(){}

    public AuthenticatedUserToken(String userId, String sessionToken) {
        this.userId = userId;
        this.token = sessionToken;
    }

    public String getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
