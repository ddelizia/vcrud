package org.ddelizia.vcrud.core.social.service.impl;

import org.ddelizia.vcrud.core.repository.UserRepository;
import org.ddelizia.vcrud.core.social.SocialConnectionRepository;
import org.ddelizia.vcrud.core.social.service.SocialConnectionService;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 15:34
 * To change this template use File | Settings | File Templates.
 */
public class SocialConnectionServiceImpl implements SocialConnectionService{

    @Autowired
    private SocialConnectionRepository socialConnectionRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public AuthenticatedUserToken socialLogin(Connection<?> connection) {
        List<String> userUuids =
                socialConnectionRepository.findUserIdsWithConnection(connection);
        if(userUuids.size() == 0) {
            throw new AuthenticationException();
        }
        User user = userRepository.findByName(userUuids.get(0));
        if (user == null) {
            throw new AuthenticationException();
        }
        updateUserFromProfile(connection, user);
        return new AuthenticatedUserToken(user.getUuid().toString(), user.addSessionToken().getToken());
    }

    private void updateUserFromProfile(Connection<?> connection, User user) {
        UserProfile profile = connection.fetchUserProfile();
        user.setEmail(profile.getEmail());
        user.setFirstName(profile.getFirstName());
        user.setLastName(profile.getLastName());
        //users logging in from social network are already verified
        user.setVerified(true);
        if(user.hasRole(Role.anonymous)) {
            user.setRole(Role.authenticated);
        }
        userRepository.save(user);
    }
}
