package org.ddelizia.vcrud.core.social.service.impl;

import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.repository.SessionTokenRepository;
import org.ddelizia.vcrud.core.social.repository.SocialUsersConnectionRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.security.exception.AuthenticationException;
import org.ddelizia.vcrud.core.social.repository.SocialConnectionRepository;
import org.ddelizia.vcrud.core.social.service.SocialConnectionService;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */

@Service(SocialConnectionService.DEFAULT_BEAN_NAME)
public class SocialConnectionServiceImpl implements SocialConnectionService{

    @Autowired
    private SocialUsersConnectionRepository socialUsersConnectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    @Autowired
    private SessionTokenRepository sessionTokenRepository;



    @Override
    public AuthenticatedUserToken socialLogin(Connection<?> connection) {
        List<String> userUuids =
                socialUsersConnectionRepository.findUserIdsWithConnection(connection);
        if(userUuids.size() == 0) {
            throw new AuthenticationException();
        }
        User user = userRepository.findByName(userUuids.get(0));
        if (user == null) {
            throw new AuthenticationException();
        }
        updateUserFromProfile(connection, user);
        SessionToken sessionToken = new SessionToken(user);
        sessionTokenRepository.save(sessionToken);
        return new AuthenticatedUserToken(user.getId(), sessionToken.getToken());
    }

    private void updateUserFromProfile(Connection<?> connection, User user) {
        UserProfile profile = connection.fetchUserProfile();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        //users logging in from social network are already verified
        userService.verifyUser(user);
        if(userService.isAnonymousUser(user)){
            userService.assignGroupToUser(
                    AppConfig.USER_USERGROUP_SOCIAL,
                    user.getId()
            );
        }
        userRepository.save(user);
    }

    public void setSocialUsersConnectionRepository(SocialUsersConnectionRepository socialUsersConnectionRepository) {
        this.socialUsersConnectionRepository = socialUsersConnectionRepository;
    }
}
