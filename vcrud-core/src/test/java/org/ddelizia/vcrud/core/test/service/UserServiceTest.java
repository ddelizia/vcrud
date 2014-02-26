package org.ddelizia.vcrud.core.test.service;

import org.ddelizia.vcrud.core.test.util.UserManagmentDataFactory;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.ddelizia.vcrud.core.usermanagement.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 25/02/14
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceTest {


    private UserGroupService userGroupService;
    private UserGroupRepository userGroupRepository;
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setUp(){
        userGroupService = Mockito.mock(UserGroupService.class);
        userGroupRepository = Mockito.mock(UserGroupRepository.class);
        userRepository = Mockito.mock(UserRepository.class);

        userService = new UserServiceImpl();
        ((UserServiceImpl)userService).setUserGroupRepository(userGroupRepository);
        ((UserServiceImpl)userService).setUserGroupService(userGroupService);
        ((UserServiceImpl)userService).setUserRepository(userRepository);

        //Mockito.when()
    }

    public void isRestUser (){}

    public void isAuthenticatedUser (){}

    public void isAnonymousUser (){}

    public void isAccountLocked (){}

    public void isAccountExpired (){}

    public void isAccountCredentialsExpired (){}

    @Test
    public void isVerified(){
        User user = userRepository.findByName(UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_NAME);
        Assert.assertNotNull(user);

        Assert.assertTrue(userService.isVerified(user));
    }

    public void createCustomerInGroup(){}

    public void assignGroupToUser(){}

    public void verifyUser(){}

    //private createTestCustomer(name)

}
