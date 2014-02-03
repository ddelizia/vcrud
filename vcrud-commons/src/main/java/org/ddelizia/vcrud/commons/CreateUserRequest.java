package org.ddelizia.vcrud.commons;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 16:49
 * To change this template use File | Settings | File Templates.
 */
public class CreateUserRequest {

    private ExternalUser user;
    private PasswordRequest password;


    public CreateUserRequest() {
    }

    public CreateUserRequest(final ExternalUser user, final PasswordRequest password) {
        this.user = user;
        this.password = password;
    }

    public ExternalUser getUser() {
        return user;
    }

    public void setUser(ExternalUser user) {
        this.user = user;
    }

    public PasswordRequest getPassword() {
        return password;
    }

    public void setPassword(PasswordRequest password) {
        this.password = password;
    }
}
