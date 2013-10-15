package org.ddelizia.vcrud.core.service.social;

import org.ddelizia.vcrud.core.dao.SocialUserDao;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.user.UserService;
import org.ddelizia.vcrud.core.utils.DynamicComparator;
import org.ddelizia.vcrud.model.social.SocialUser;
import org.ddelizia.vcrud.model.social.SocialUser_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 13/08/13
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */

public class VcrudConnectionRepository implements ConnectionRepository {

    @Autowired
    private ModelService modelService;

    @Autowired
    private SocialUserDao socialUserDao;

    @Autowired
    private UserService userService;

    private User currentUser;

    private final String userId;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();

    private final TextEncryptor textEncryptor;

    public VcrudConnectionRepository(String userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.userId=userId;
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor=textEncryptor;
    }

    @Override
    public MultiValueMap<String, Connection<?>> findAllConnections() {
        List<Connection<?>> resultList = connectionMapper.mapEntities(
                modelService.getModels(SocialUser_.user.getName(),
                        findUser(),
                        SocialUser.class,
                        null,
                        null)
        );
        MultiValueMap<String,Connection<?>> connections = new LinkedMultiValueMap<String, Connection<?>>();
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
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SocialUser_.user.getName(), findUser());
        parameters.put(SocialUser_.providerId.getName(), providerId);
        List <SocialUser> resultCollection = modelService.getModels(parameters, SocialUser.class, null, null);
        Collections.sort(resultCollection
                , new DynamicComparator(SocialUser_.rank.getName())
        );
        return connectionMapper.mapEntities(resultCollection);
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


        List<Connection<?>> resultList = connectionMapper.mapEntities(socialUserDao.findIn(findUser(),providerUsers));

        MultiValueMap<String, Connection<?>> connectionsForUsers = new LinkedMultiValueMap<String, Connection<?>>();
        for (Connection<?> connection : resultList) {
            String providerId = connection.getKey().getProviderId();
            List<String> userIds = providerUsers.get(providerId);
            List<Connection<?>> connections = connectionsForUsers.get(providerId);
            if (connections == null) {
                connections = new ArrayList<Connection<?>>(userIds.size());
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
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(SocialUser_.user.getName(),findUser());
            parameters.put(SocialUser_.providerId.getName(), connectionKey.getProviderId());
            parameters.put(SocialUser_.providerUserId.getName(), connectionKey.getProviderUserId());

            return connectionMapper.mapEntity(modelService.getModel(parameters, SocialUser.class));
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
    @Transactional
    public void addConnection(Connection<?> connection) {
        try {
            ConnectionData data = connection.createData();

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put(SocialUser_.user.getName(), findUser());
            parameters.put(SocialUser_.providerId.getName(), data.getProviderId());
            parameters.put(SocialUser_.providerUserId.getName(), data.getProviderUserId());

            List <SocialUser> socialUsers = modelService.getModels(parameters, SocialUser.class, null, null);

            Integer currentRank = 1;

            if(!socialUsers.isEmpty()){
                SocialUser maxrank = Collections.max(socialUsers, new DynamicComparator(SocialUser_.rank.getName()));
                currentRank = maxrank.getRank() + 1 ;
            }



            SocialUser socialUser = new SocialUser();
            socialUser.setUser(findUser());
            socialUser.setProviderId(data.getProviderId());
            socialUser.setProviderUserId(data.getProviderUserId());
            socialUser.setRank(currentRank);
            socialUser.setDisplayName(data.getDisplayName());
            socialUser.setImageUrl(data.getImageUrl());
            socialUser.setProfileUrl(data.getProfileUrl());
            socialUser.setAccessToken(encrypt(data.getAccessToken()));
            socialUser.setSecret(encrypt(data.getSecret()));
            socialUser.setRefreshToken(encrypt(data.getRefreshToken()));
            if (data.getExpireTime()!=null){
                socialUser.setExpireTime(new Date(data.getExpireTime()));
            }
            modelService.persist(socialUser);
        } catch (DuplicateKeyException e) {
            throw new DuplicateConnectionException(connection.getKey());
        }
    }

    @Override
    @Transactional
    public void updateConnection(Connection<?> connection) {
        ConnectionData data = connection.createData();

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SocialUser_.user.getName(), findUser());
        parameters.put(SocialUser_.providerId.getName(), data.getProviderId());
        parameters.put(SocialUser_.providerUserId.getName(), data.getProviderUserId());
        SocialUser socialUser = modelService.getModel(parameters, SocialUser.class);

        if(socialUser != null){
            socialUser.setDisplayName(data.getDisplayName());
            socialUser.setProfileUrl(data.getProfileUrl());
            socialUser.setImageUrl(data.getImageUrl());
            socialUser.setAccessToken(encrypt(data.getAccessToken()));
            socialUser.setSecret(encrypt(data.getSecret()));
            socialUser.setRefreshToken(encrypt(data.getRefreshToken()));
            if (data.getExpireTime()!=null){
                socialUser.setExpireTime(new Date(data.getExpireTime()));
            }
            modelService.persist(socialUser);
        }
    }

    @Override
    @Transactional
    public void removeConnections(String s) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SocialUser_.user.getName(), findUser());
        parameters.put(SocialUser_.providerId.getName(), s);
        List<SocialUser> socialUsers = modelService.getModels(parameters, SocialUser.class, null, null);

        for(SocialUser socialUser:socialUsers){
            modelService.remove(socialUser);
        }
    }

    @Override
    public void removeConnection(ConnectionKey connectionKey) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SocialUser_.user.getName(), findUser());
        parameters.put(SocialUser_.providerId.getName(), connectionKey.getProviderId());
        parameters.put(SocialUser_.providerUserId.getName(), connectionKey.getProviderUserId());
        SocialUser socialUser = modelService.getModel(parameters, SocialUser.class);

        if (socialUser!=null){
            modelService.remove(socialUser);
        }
    }

    private User findUser(){
        if (currentUser==null){
            currentUser = userService.getUserByUsernameOrEmail(userId);
        }
        return currentUser;
    }

    private Connection<?> findPrimaryConnection(String providerId) {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put(SocialUser_.user.getName(), findUser());
        parameters.put(SocialUser_.providerId.getName(), providerId);
        parameters.put(SocialUser_.rank.getName(), new Integer(1));
        List<SocialUser> socialUsers = modelService.getModels(parameters, SocialUser.class, null, null);

        List<Connection<?>> connections = connectionMapper.mapEntities(socialUsers);
        if (connections.size() > 0) {
            return connections.get(0);
        } else {
            return null;
        }
    }

    private final class ServiceProviderConnectionMapper {

        public List<Connection<?>> mapEntities(List<SocialUser> socialUsers){
            List<Connection<?>> result = new ArrayList<Connection<?>>();
            for(SocialUser su : socialUsers){
                result.add(mapEntity(su));
            }
            return result;
        }

        public Connection<?> mapEntity(SocialUser socialUser){
            ConnectionData connectionData = mapConnectionData(socialUser);
            ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
            return connectionFactory.createConnection(connectionData);
        }

        private ConnectionData mapConnectionData(SocialUser socialUser){
            return new ConnectionData(socialUser.getProviderId(), socialUser.getAccessToken(), socialUser.getDisplayName(), socialUser.getProfileUrl(), socialUser.getImageUrl(),
                    decrypt(socialUser.getAccessToken()), decrypt(socialUser.getSecret()), decrypt(socialUser.getRefreshToken()), expireTime(socialUser.getExpireTime().getTime()));
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
}