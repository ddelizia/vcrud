package org.ddelizia.vcrud.core.test.usermagement.repository;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.test.util.UserManagmentDataFactory;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 24/02/14
 * Time: 22:02
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupRepositoryTest extends AbstractJunit4Vcrud {

    private static final Logger LOGGER = Logger.getLogger(UserRepositoryTest.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserManagmentDataFactory userManagmentDataFactory;

    @Autowired
    private AppConfig appConfig;

    @Test
    public void findByGroupName(){
        String roleAuth = appConfig.getProperty(AppConfig.USER_USERGROUP_AUTHENTICATED, String.class, null);
        UserGroup userGroup = userGroupRepository.findByGroupName(
                roleAuth
            );
        Assert.assertNotNull(userGroup);
        Assert.assertEquals(userGroup.getGroupName(), roleAuth);
    }

    //@Test
    public void findUserBelongsToGroup(){
        User user = userRepository.findByName(UserManagmentDataFactory.CUSTOMER_VERIFIED_RESTGROUP_NAME);
        Assert.assertNotNull(user);

        String roleAuth = appConfig.getProperty(AppConfig.USER_USERGROUP_AUTHENTICATED, String.class, null);
        UserGroup userGroupAuth = userGroupRepository.findByGroupName(
                roleAuth
        );

        String roleRest = appConfig.getProperty(AppConfig.USER_USERGROUP_REST, String.class, null);
        UserGroup userGroupRest = userGroupRepository.findByGroupName(
                roleRest
        );

        Assert.assertTrue(userGroupRepository.findUserBelongsToGroup(user, userGroupAuth));
        Assert.assertTrue(userGroupRepository.findUserBelongsToGroup(user, userGroupRest));
    }

    @Test
    public void findByFather(){
        String roleAuth = appConfig.getProperty(AppConfig.USER_USERGROUP_AUTHENTICATED, String.class, null);
        UserGroup userGroupAuth = userGroupRepository.findByGroupName(
                roleAuth
        );

        String roleRest = appConfig.getProperty(AppConfig.USER_USERGROUP_REST, String.class, null);

        List<UserGroup> list =  userGroupRepository.findByFather(userGroupAuth);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()==1);
        Assert.assertTrue(list.iterator().next().getName().equals(roleRest));
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
