package org.ddelizia.vcrud.core.test.usermagement.repository;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.test.util.UserManagmentDataFactory;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ddelizia
 * @since 18/02/14 12:33
 */
public class UserRepositoryTest extends AbstractJunit4Vcrud{

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserManagmentDataFactory userManagmentDataFactory;

    @Test
    public void findByName(){
        User user = userRepository.findByName(UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_NAME);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_NAME);
    }

    @Test
    public void findByEmail (){
        User user = userRepository.findByEmail(UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_EMAIL);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getEmail(), UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_EMAIL);
    }

    /**
     * Implement this method instead of @After
     */
    @Override
    public void vcrudAfter() {
        userManagmentDataFactory.removeData();
    }

    /**
     * Implement this method instead of @Before
     */
    @Override
    public void vcrudBefore() {
        userManagmentDataFactory.createData();
    }
}
