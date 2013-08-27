package org.ddelizia.vcrud.core.test.service;

import org.ddelizia.vcrud.core.service.user.UserService;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 23/08/13
 * Time: 15:36
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest extends AbstractJunit4Vcrud{

    @Autowired
    private UserService userService;

    private static final String USERNAME = "test2";
    private static final String EMAIL = "email2@email.it";
    private static final String PASSWORD = "testPass";

    private static final String USERNAME2 = "test";
    private static final String EMAIL2 = "email@email.it";
    private static final String PASSWORD2 = "testPass";

    @Override
    public void vcrudBefore() {

    }

    @Test
    public void registerUser(){
        userService.registerUser(USERNAME, EMAIL, PASSWORD, PASSWORD, null);

        User user = userService.getUserByUsernameOrEmail(USERNAME);

        Assert.assertEquals(new Md5PasswordEncoder().encodePassword(PASSWORD, null), user.getPassword());
    }

    @Test
    public void loginUser(){
        userService.registerUser(USERNAME2, EMAIL2, PASSWORD2, PASSWORD2, null);

        User user = userService.vcrudLogIn(USERNAME2, PASSWORD2, null);
        Assert.assertNotNull(user);

        User user2 = userService.vcrudLogIn(EMAIL2, PASSWORD2, null);
        Assert.assertNotNull(user2);
    }
}
