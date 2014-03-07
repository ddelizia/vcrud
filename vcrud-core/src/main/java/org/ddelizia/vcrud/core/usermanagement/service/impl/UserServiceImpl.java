package org.ddelizia.vcrud.core.usermanagement.service.impl;

import com.google.common.collect.Sets;
import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.commons.client.CreateUserRequest;
import org.ddelizia.vcrud.commons.client.LoginRequest;
import org.ddelizia.vcrud.core.basic.service.AbstractService;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.exception.AuthenticationException;
import org.ddelizia.vcrud.core.security.exception.AuthorizationException;
import org.ddelizia.vcrud.core.security.exception.UserNotFoundException;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.service.CryptoService;
import org.ddelizia.vcrud.core.security.service.SessionTokenService;
import org.ddelizia.vcrud.core.security.service.converter.ConverterUser2ExternalUser;
import org.ddelizia.vcrud.core.usermanagement.exception.DuplicateUserException;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.ddelizia.vcrud.core.usermanagement.service.converter.ConverterCreateUserRequest2User;
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
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

	@Autowired
	@Qualifier(UserGroupService.DEFAULT_BEAN_NAME)
	private UserGroupService userGroupService;

    @Autowired
    @Qualifier(CryptoService.DEFAULT_BEAN_NAME)
    private CryptoService cryptoService;

	@Autowired
	@Qualifier(SessionTokenService.DEFAULT_BEAN_NAME)
	private SessionTokenService sessionTokenService;

	@Autowired
	private ConverterUser2ExternalUser converterUser2ExternalUser;

	@Autowired
	private ConverterCreateUserRequest2User converterCreateUserRequest2User;

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

    @Override
    public AuthenticatedUserToken createUser(CreateUserRequest request, UserGroup userGroup) {
        validate(request);
        User searchedForUser = userRepository.findByEmail(request.getUser().getEmailAddress());
        if (searchedForUser != null) {
            throw new DuplicateUserException();
        }

        User newUser = createNewUser(request, userGroup);
	    userRepository.save(newUser);
        AuthenticatedUserToken token = new AuthenticatedUserToken(newUser.getId().toString(), sessionTokenService.createSessionToken(newUser).getToken());
        return token;
    }

    @Override
    public AuthenticatedUserToken createCustomer(UserGroup userGroup) {
        User user = createCustomerInGroup(userGroup);
        AuthenticatedUserToken token = new AuthenticatedUserToken(user.getId().toString(), sessionTokenService.createSessionToken(user).getToken());
        userRepository.save(user);
        return token;
    }

    @Override
    public AuthenticatedUserToken login(LoginRequest request) {
        validate(request);
        User user = null;
        user = userRepository.findByEmail(request.getUsername());
        if (user == null) {
            throw new AuthenticationException();
        }
        String hashedPassword = null;
        try {
            hashedPassword = cryptoService.hashPassword(
                    request.getPassword(),
                    user);
        } catch (Exception e) {
            throw new AuthenticationException();
        }
        if (hashedPassword.equals(user.getPassword())) {
            return new AuthenticatedUserToken(user.getId(), sessionTokenService.createSessionToken(user).getToken());
        } else {
            throw new AuthenticationException();
        }
    }

    @Override
    public ExternalUser getUser(ExternalUser requestingUser, String userIdentifier) {
        Assert.notNull(requestingUser);
        Assert.notNull(userIdentifier);
        User user = ensureUserIsLoaded(userIdentifier);
        if(!requestingUser.getId().equals(user.getId()) && !requestingUser.getRoles().isEmpty())  {
            throw new AuthorizationException("User not authorized to load profile");
        }
        return converterUser2ExternalUser.convert(user);
    }

	private User ensureUserIsLoaded(String userId){
		User user = userRepository.findOne(userId);
		if (user == null){
			user = userRepository.findByName(userId);
			if (user == null){
				user = userRepository.findByEmail(userId);
			}else{
				throw new UserNotFoundException();
			}
		}
		return user;
	}

	private User createNewUser(CreateUserRequest request, UserGroup userGroup) {
		User userToSave = converterCreateUserRequest2User.convert(request);
		try {
			String hashedPassword = cryptoService.hashPassword(
					request.getPassword().getPassword(),
					userToSave);
			userToSave.setPassword(hashedPassword);
		}  catch (Exception e) {
			throw new AuthenticationException();
		}
		userToSave.setUserGroups(Sets.newHashSet(userGroup));
		return userToSave;
	}

    public void setUserGroupRepository(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    public void setUserGroupService(UserGroupService userGroupService) {
        this.userGroupService = userGroupService;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void setCryptoService(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

	public void setSessionTokenService(SessionTokenService sessionTokenService) {
		this.sessionTokenService = sessionTokenService;
	}

	public void setConverterUser2ExternalUser(ConverterUser2ExternalUser converterUser2ExternalUser) {
		this.converterUser2ExternalUser = converterUser2ExternalUser;
	}

	public void setConverterCreateUserRequest2User(ConverterCreateUserRequest2User converterCreateUserRequest2User) {
		this.converterCreateUserRequest2User = converterCreateUserRequest2User;
	}
}
