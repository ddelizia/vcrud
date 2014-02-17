package org.ddelizia.vcrud.core.usermanagement.service;

import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 09/02/14
 * Time: 12:06
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public static final String DEFAULT_BEAN_NAME = "defaultUserService";

    public boolean isRestUser (User user);

    public boolean isAuthenticatedUser (User user);

    public boolean isAnonymousUser (User user);

    public boolean isAccountLocked (User user);

    public boolean isAccountExpired (User user);

    public boolean isAccountCredentialsExpired (User user);

    /**
     * An user is verified if satisfy the following things:
     *  - is not Anonymous
     *  - account is not locked
     *  - acoound is not expired
     *  - credentials are not expired
     *  - account is enabled
     * @param user
     * @return
     */
    public boolean isVerified(User user);

    public Customer createCustomerInGroup(UserGroup userGroup);

    public User assignGroupToUser (String groupName, String userId);

    public void verifyUser(User user);

}
