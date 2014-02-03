package org.ddelizia.vcrud.commons;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 18:37
 * To change this template use File | Settings | File Templates.
 */
public class PasswordRequest {

    private String password;

    public PasswordRequest() {}

    public PasswordRequest(final String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
