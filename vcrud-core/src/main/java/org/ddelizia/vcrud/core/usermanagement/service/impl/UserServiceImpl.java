package org.ddelizia.vcrud.core.usermanagement.service.impl;

import org.ddelizia.vcrud.core.basic.service.AbstractService;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 09/02/14
 * Time: 12:09
 * To change this template use File | Settings | File Templates.
 */

@Service(UserService.DEFAULT_BEAN_NAME)
public class UserServiceImpl extends AbstractService implements UserService {

    @Autowired
    @Qualifier(UserGroupService.DEFAULT_BEAN_NAME)
    private UserGroupService userGroupService;

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isRestUser(User user) {
        Assert.notNull(user);

        return userGroupRepository.findUserBelongsToGroup(
                user,
                userGroupService.getUserGroupFromProperty(AppConfig.USER_USERGROUP_REST)
        );
    }

    @Override
    public boolean isAuthenticatedUser(User user) {
        Assert.notNull(user);

        return userGroupRepository.findUserBelongsToGroup(
                user,
                userGroupService.getUserGroupFromProperty(AppConfig.USER_USERGROUP_REST)
        );
    }

    @Override
    public boolean isAnonymousUser(User user) {
        Assert.notNull(user);

        return CollectionUtils.isEmpty(user.getUserGroups());
    }

    /**
     * if user.isAccountLocked == null then return false
     * @param user
     * @return
     */
    @Override
    public boolean isAccountLocked(User user) {
        Assert.notNull(user);
        if (user.getAccountLocked() == null){
            return false;
        }
        return user.getAccountLocked();
    }

    /**
     * if user.getExpireDate == null then return false
     * @param user
     * @return
     */
    @Override
    public boolean isAccountExpired(User user) {
        Assert.notNull(user);
        if (user.getExpireDate() == null){
            return false;
        }
        return user.getExpireDate().isBeforeNow();
    }

    /**
     * if user.getCredentialsExpireTime == null then return false
     * @param user
     * @return
     */
    @Override
    public boolean isAccountCredentialsExpired(User user) {
        Assert.notNull(user);
        if (user.getCredentialsExpireTime() == null){
            return false;
        }
        return user.getCredentialsExpireTime().isBeforeNow();
    }

    @Override
    public boolean isVerified(User user){
        Assert.notNull(user);
        return user.getEnabled()
                && !isAccountCredentialsExpired(user)
                && !isAccountLocked(user)
                && !isAccountExpired(user)
                && !isAnonymousUser(user);

    }

    @Override
    public Customer createCustomerInGroup(UserGroup userGroup) {
        Customer customer = new Customer();
        customer.getUserGroups().add(userGroup);
        userRepository.save(customer);
        return customer;
    }

    @Override
    public User assignGroupToUser(String groupName, String userId) {
        Assert.notNull(groupName);
        Assert.notNull(userId);

        UserGroup userGroup = userGroupRepository.findByGroupName(groupName);
        User user = userRepository.findOne(userId);

        user.getUserGroups().add(userGroup);
        userRepository.save(user);

        return user;
    }

    @Override
    public void verifyUser(User user) {
        user.setExpireDate(null);
        user.setEnabled(true);
        user.setAccountLocked(false);
        user.setCredentialsExpireTime(null);
        userRepository.save(user);
    }

    public void setUserGroupRepository(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }
}
