package org.ddelizia.vcrud.core.social.repository;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ddelizia
 * @since 18/02/14 15:21
 */
public class MongoSocialUsersConnectionRepository implements UsersConnectionRepository{

    private static final Logger LOGGER = Logger.getLogger(MongoSocialUsersConnectionRepository.class);

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    @Resource
    private SocialUserConnectionRepository socialUserConnectionRepository;

	@Resource
	private UserRepository userRepository;

	@Resource(name = UserGroupService.DEFAULT_BEAN_NAME)
	private UserGroupService userGroupService;

	@Resource(name = UserService.DEFAULT_BEAN_NAME)
	private UserService userService;

    public MongoSocialUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    /**
     * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
     * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
     * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
     * @see #findUserIdsWithConnection(Connection)
     */
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> usrs = new ArrayList<>();
        ConnectionKey key = connection.getKey();
        List<SocialUserConnection> socialUserConnections =
                socialUserConnectionRepository.findByProviderIdAndProviderUserId(
                        key.getProviderId(),
                        key.getProviderUserId()
                );
        if(!socialUserConnections.isEmpty()){
            for(SocialUserConnection  socialUserConnection: socialUserConnections){
                usrs.add(socialUserConnection.getUser().getId());
            }
            return usrs;
        }

	    //First time connected so create a User account or find one that is already created with the email address
	    User user = findUserFromSocialProfile(connection);
	    String userId;
	    if(user == null) {
		    userId = userService.createCustomerInGroup(userGroupService.getAuthenticatedGroup()).getId();
	    } else {
		    userId = user.getId();
	    }

	    //persist the Connection
	    ConnectionRepository connectionRepository = createConnectionRepository(userId);
	    connectionRepository.addConnection(connection);
	    usrs.add(userId);

        //if empty we should go to the sign up form
        return usrs;
    }

    public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
        return socialUserConnectionRepository.findUsersConnectedTo(providerId, providerUserIds);
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new MongoSocialConnectionRepository(socialUserConnectionRepository, userRepository, userId, connectionFactoryLocator, textEncryptor);
    }

	private User findUserFromSocialProfile(Connection connection) {
		User user = null;
		UserProfile profile = connection.fetchUserProfile();
		if(profile != null && StringUtils.hasText(profile.getEmail())) {
			user = userRepository.findByEmail(profile.getEmail());
		}
		return user;
	}

	public void setSocialUserConnectionRepository(SocialUserConnectionRepository socialUserConnectionRepository) {
		this.socialUserConnectionRepository = socialUserConnectionRepository;
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setUserGroupService(UserGroupService userGroupService) {
		this.userGroupService = userGroupService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
