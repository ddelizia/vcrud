package org.ddelizia.vcrud.core.social.repository;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author ddelizia
 * @since 18/02/14 15:21
 */
public class SocialUsersConnectionRepository implements UsersConnectionRepository{

    private static final Logger LOGGER = Logger.getLogger(SocialUsersConnectionRepository.class);

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    public SocialUsersConnectionRepository( ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    /**
     * The command to execute to create a new local user profile in the event no user id could be mapped to a connection.
     * Allows for implicitly creating a user profile from connection data during a provider sign-in attempt.
     * Defaults to null, indicating explicit sign-up will be required to complete the provider sign-in attempt.
     * @see #findUserIdWithConnection(Connection)
     */
    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

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

        if (connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if(newUserId == null)
                //auto signup failed, so we need to go to a sign up form
                return usrs;
            createConnectionRepository(newUserId).addConnection(connection);
            usrs.add(newUserId);
        }
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
        return new SocialConnectionRepository(userId, connectionFactoryLocator, textEncryptor);
    }

    public void setSocialUserConnectionRepository(SocialUserConnectionRepository socialUserConnectionRepository) {
        this.socialUserConnectionRepository = socialUserConnectionRepository;
    }
}
