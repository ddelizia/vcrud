package org.ddelizia.vcrud.email.handler;

import org.springframework.beans.factory.annotation.Required;
import org.subethamail.smtp.auth.LoginFailedException;
import org.subethamail.smtp.auth.UsernamePasswordValidator;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:55
 * To change this template use File | Settings | File Templates.
 */
public class SimpleUserValidationHandler implements UsernamePasswordValidator{

    private String username;
    private String password;

    @Override
    public void login(String username, String password) throws LoginFailedException {
        if (!(this.username.equals(username) || this.password.equals(password))){
            throw new LoginFailedException("Username And Password");
        }
    }

    public String getUsername() {
        return username;
    }

    @Required
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    @Required
    public void setPassword(String password) {
        this.password = password;
    }
}
