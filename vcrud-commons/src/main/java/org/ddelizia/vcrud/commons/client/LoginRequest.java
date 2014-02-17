package org.ddelizia.vcrud.commons.client;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 18:26
 * To change this template use File | Settings | File Templates.
 */
public class LoginRequest {

    private String username;
    private String password;

    public LoginRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
