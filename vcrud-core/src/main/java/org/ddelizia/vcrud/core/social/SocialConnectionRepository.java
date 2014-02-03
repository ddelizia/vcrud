package org.ddelizia.vcrud.core.social;

import org.ddelizia.vcrud.core.repository.SocialUserConnectionRepository;
import org.ddelizia.vcrud.core.repository.UserRepository;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.DuplicateConnectionException;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.social.connect.UserProfile;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 11:42
 * To change this template use File | Settings | File Templates.
 */
public class SocialConnectionRepository implements ConnectionRepository{

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Autowired
    private UserRepository userRepository;


    private final String userId;
    private final TextEncryptor textEncryptor;
    private final ConnectionFactoryLocator connectionFactoryLocator;
    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

    public SocialConnectionRepository(String userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.userId=userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor=textEncryptor;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<Connection<?>> resultList = connectionMapper.mapEntities(
                socialUserConnectionRepository.findByUserName(userId)
        );
        MultiValueMap<String,Connection<?>> connections = new LinkedMultiValueMap<>();
        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
        for (String registeredProviderId : registeredProviderIds) {
            connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
        }
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            if (connections.get(providerId).size() == 0) {
                connections.put(providerId, new LinkedList<Connection<?>>());
            }
            connections.add(providerId, connection);
        }
        return connections;
    }

    @Override
    public List<Connection<?>> findConnections(String providerId) {
        return connectionMapper.mapEntities(socialUserConnectionRepository.findByUserNameAndProviderId(userId, providerId));
    }

    @Override
    public <A> List<Connection<A>> findConnections(Class<A> apiType) {
        List<?> connections = findConnections(getProviderId(apiType));
        return (List<Connection<A>>) connections;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUsers) {
        if (providerUsers.isEmpty()) {
            throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
        }

        List<Connection<?>> resultList = connectionMapper.mapEntities(
                socialUserConnectionRepository.findByUserNameInProviderUsers(userId,providerUsers));

        MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<>();
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            List<String> userIds = providerUsers.get(providerId);
            List<Connection<?>> connections = connectionsForUsers.get(providerId);
            if (connections == null) {
                connections = new ArrayList<>(userIds.size());
                for (int i = 0; i < userIds.size(); i++) {
                    connections.add(null);
                }
                connectionsForUsers.put(providerId, connections);
            }
            String providerUserId = connection.getKey().getProviderUserId();
            int connectionIndex = userIds.indexOf(providerUserId);
            connections.set(connectionIndex, connection);
        }
        return connectionsForUsers;
    }


    @Override
    public Connection<?> getConnection(ConnectionKey connectionKey) {
        try {
            return connectionMapper.mapEntity(
                    socialUserConnectionRepository.findByUserNameAndProviderIdAndProviderUserId(
                            userId,
                            connectionKey.getProviderId(),
                            connectionKey.getProviderUserId()
                    )
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NoSuchConnectionException(connectionKey);
        }
    }

    @Override
    public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) getConnection(new ConnectionKey(providerId, providerUserId));
    }

    @Override
    public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        Connection<A> connection = (Connection<A>) findPrimaryConnection(providerId);
        if (connection == null) {
            throw new NotConnectedException(providerId);
        }
        return connection;
    }

    @Override
    public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
        String providerId = getProviderId(apiType);
        return (Connection<A>) findPrimaryConnection(providerId);
    }

    @Override
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();

            SocialUserConnection socialUserConnectionMax = socialUserConnectionRepository.findByUserNameAndProviderIdMaxRank(
                    userId,
                    data.getProviderId()
            );

            SocialUserConnection socialUserConnection = new SocialUserConnection();
            socialUserConnection.setUser(userRepository.findByName(userId));
            socialUserConnection.setProviderId(data.getProviderId());
            socialUserConnection.setProviderUserId(data.getProviderUserId());
            socialUserConnection.setRank(socialUserConnectionMax.getRank());
            socialUserConnection.setDisplayName(data.getDisplayName());
            socialUserConnection.setImageUrl(data.getImageUrl());
            socialUserConnection.setProfileUrl(data.getProfileUrl());
            socialUserConnection.setAccessToken(encrypt(data.getAccessToken()));
            socialUserConnection.setSecret(encrypt(data.getSecret()));
            socialUserConnection.setRefreshToken(encrypt(data.getRefreshToken()));
            socialUserConnection.setExpireTime(new DateTime(data.getExpireTime()));
            socialUserConnectionRepository.save(socialUserConnection);
        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }

    @Override
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();

        SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserNameAndProviderIdAndProviderUserId(
                userId,
                data.getProviderId(),
                data.getProviderUserId()
        );

        if(socialUserConnection != null){
            socialUserConnection.setDisplayName(data.getDisplayName());
            socialUserConnection.setProfileUrl(data.getProfileUrl());
            socialUserConnection.setImageUrl(data.getImageUrl());
            socialUserConnection.setAccessToken(encrypt(data.getAccessToken()));
            socialUserConnection.setSecret(encrypt(data.getSecret()));
            socialUserConnection.setRefreshToken(encrypt(data.getRefreshToken()));
            socialUserConnection.setExpireTime(new DateTime(data.getExpireTime()));

            socialUserConnectionRepository.save(socialUserConnection);
        }
    }

    @Override
    public void removeConnections(String s) {
        socialUserConnectionRepository.delete(
                socialUserConnectionRepository.findByUserNameAndProviderId(userId, s)
        );
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        socialUserConnectionRepository.delete(
                socialUserConnectionRepository.findByUserNameAndProviderIdAndProviderUserId(
                        userId,
                        connectionKey.getProviderId(),
                        connectionKey.getProviderUserId())
        );
    }

    /**
     * Find User with the Connection profile (providerId and providerUserId)
     * If this is the first connection attempt there will be nor User so create one and
     * persist the Connection information
     * In reality there will only be one User associated with the Connection
     *
     * @param connection
     * @return List of User Ids (see User.getUuid())
     */
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> userIds = new ArrayList<String>();
        ConnectionKey key = connection.getKey();
        List<SocialUserConnection> users =
                socialUserConnectionRepository.findByProviderIdAndProviderUserId
                        (key.getProviderId(), key.getProviderUserId());
        if (!users.isEmpty()) {
            for (SocialUserConnection user : users) {
                userIds.add(user.getUser().getName());
            }
            return userIds;
        }

        User user = findUserFromSocialProfile(connection);
        String userId;
        if(user == null) {
            userId = userService.createUser(Role.authenticated).getId();
        } else {
            userId = user.getName();
        }
        //persist the Connection
        createConnectionRepository(userId).addConnection(connection);
        userIds.add(userId);
        return userIds;
    }

    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        User user = userRepository.findByName(userId);
        if(user == null) {
            throw new IllegalArgumentException("User not Found");
        }
        return new SocialConnectionRepository(userId, connectionFactoryLocator, textEncryptor);
    }

    private User findUserFromSocialProfile(Connection connection) {
        User user = null;
        UserProfile profile = connection.fetchUserProfile();
        if(profile != null && StringUtils.hasText(profile.getEmail())) {
            user = userRepository.findByEmail(profile.getEmail());
        }
        return user;
    }


    private final class ServiceProviderConnectionMapper {

        public List<Connection<?>> mapEntities(List<SocialUserConnection> socialUsers){
            List<Connection<?>> result = new ArrayList<>();
            for(SocialUserConnection su : socialUsers){
                result.add(mapEntity(su));
            }
            return result;
        }

        public Connection<?> mapEntity(SocialUserConnection socialUser){
            ConnectionData connectionData = mapConnectionData(socialUser);
            ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(SocialUserConnection socialUser){
            return new ConnectionData(socialUser.getProviderId(), socialUser.getAccessToken(), socialUser.getDisplayName(), socialUser.getProfileUrl(), socialUser.getImageUrl(),
                    decrypt(socialUser.getAccessToken()), decrypt(socialUser.getSecret()), decrypt(socialUser.getRefreshToken()), expireTime(socialUser.getExpireTime().getMillis()));
        }

        private String decrypt(String encryptedText) {
            return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
        }

        private Long expireTime(Long expireTime) {
            return expireTime == null || expireTime == 0 ? null : expireTime;
        }

    }

    private <A> String getProviderId(Class<A> apiType) {
        return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
    }

    private String encrypt(String text) {
        return text != null ? textEncryptor.encrypt(text) : text;
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        List<SocialUserConnection> socialUsers = socialUserConnectionRepository.findByUserNameAndProviderId(
                userId,
                providerId
        );

        List<Connection<?>> connections = connectionMapper.mapEntities(socialUsers);
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }
}
