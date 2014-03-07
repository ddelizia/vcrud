package org.ddelizia.vcrud.core.usermanagement.service;

import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.commons.client.CreateUserRequest;
import org.ddelizia.vcrud.commons.client.LoginRequest;
import org.ddelizia.vcrud.commons.client.UpdateUserRequest;
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

    /**
     * Create a new User with the given role
     *
     * @param request
     * @param userGroup
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken createUser(CreateUserRequest request, UserGroup userGroup);


    /**
     * Create a Default User with a given role
     *
     * @param userGroup
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken createCustomer(UserGroup userGroup);

    /**
     * Login a User
     *
     * @param request
     * @return AuthenticatedUserToken
     */
    public AuthenticatedUserToken login(LoginRequest request);

    /**
     * Get a User based on a unique identifier
     *
     * Identifiers supported are uuid, emailAddress
     *
     * @param userIdentifier
     * @return  User
     */
    public ExternalUser getUser(ExternalUser requestingUser, String userIdentifier);

    /**
     * Delete user, only authenticated user accounts can be deleted
     *
     * @param userMakingRequest the user authorized to delete the user
     * @param userId the id of the user to delete
     */
    //public void deleteUser(ExternalUser userMakingRequest, String userId);

    /**
     * Save User
     *
     * @param userId
     * @param request
     */
    //public ExternalUser saveUser(String userId, UpdateUserRequest request);

    /**
     * Delete all SessionToken objects that have not been accessed within the duration specified by the argument timeSinceLastUpdatedInMinutes
     *
     * @param timeSinceLastUpdatedInMinutes
     * @return the number of sessions removed
     */
    //public Integer deleteExpiredSessions(int timeSinceLastUpdatedInMinutes);

}
