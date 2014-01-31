package org.ddelizia.vcrud.core.social;

import org.ddelizia.vcrud.model.usermanagement.VcrudPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
public class SocialConnectionRepository {//implements ConnectionRepository{
//
//    @Autowired
//    private SocialUserConnectionRepository socialUserConnectionRepository;
//
//
//    private final String userId;
//    private final ConnectionFactoryLocator connectionFactoryLocator;
//    private final ServiceProviderConnectionMapper connectionMapper = new ServiceProviderConnectionMapper();
//
//    public SocialConnectionRepository(String userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
//        this.userId=userId;
//        this.connectionFactoryLocator = connectionFactoryLocator;
//        this.textEncryptor=textEncryptor;
//    }
//
//    @Override
//    public MultiValueMap<String, Connection<?>> findAllConnections() {
//        List<Connection<?>> resultList = connectionMapper.mapEntities(
//                socialUserConnectionRepository.
//
//                modelService.getModels(SocialUser_.user.getName(),
//                        modelService.getModel(User_.username.getName(),userId, VcrudPrincipal.class),
//                        SocialUser.class,
//                        null,
//                        null)
//        );
//        MultiValueMap<String,Connection<?>> connections = new LinkedMultiValueMap<>();
//        Set<String> registeredProviderIds = connectionFactoryLocator.registeredProviderIds();
//        for (String registeredProviderId : registeredProviderIds) {
//            connections.put(registeredProviderId, Collections.<Connection<?>>emptyList());
//        }
//        for (Connection<?> connection : resultList) {
//            String providerId = connection.getKey().getProviderId();
//            if (connections.get(providerId).size() == 0) {
//                connections.put(providerId, new LinkedList<Connection<?>>());
//            }
//            connections.add(providerId, connection);
//        }
//        return connections;
//    }
//
//    @Override
//    public List<Connection<?>> findConnections(String s) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public <A> List<Connection<A>> findConnections(Class<A> aClass) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> stringStringMultiValueMap) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public Connection<?> getConnection(ConnectionKey connectionKey) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public <A> Connection<A> getConnection(Class<A> aClass, String s) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public <A> Connection<A> getPrimaryConnection(Class<A> aClass) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public <A> Connection<A> findPrimaryConnection(Class<A> aClass) {
//        return null;  //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void addConnection(Connection<?> connection) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void updateConnection(Connection<?> connection) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void removeConnections(String s) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//    @Override
//    public void removeConnection(ConnectionKey connectionKey) {
//        //To change body of implemented methods use File | Settings | File Templates.
//    }
//
//
//    private final class ServiceProviderConnectionMapper {
//
//        public List<Connection<?>> mapEntities(List<SocialUserConnection> socialUsers){
//            List<Connection<?>> result = new ArrayList<>();
//            for(SocialUserConnection su : socialUsers){
//                result.add(mapEntity(su));
//            }
//            return result;
//        }
//
//        public Connection<?> mapEntity(SocialUserConnection socialUser){
//            ConnectionData connectionData = mapConnectionData(socialUser);
//            ConnectionFactory<?> connectionFactory = connectionFactoryLocator.getConnectionFactory(connectionData.getProviderId());
//            return connectionFactory.createConnection(connectionData);
//        }
//
//        private ConnectionData mapConnectionData(SocialUserConnection socialUser){
//            return new ConnectionData(socialUser.getProviderId(), socialUser.getAccessToken(), socialUser.getDisplayName(), socialUser.getProfileUrl(), socialUser.getImageUrl(),
//                    decrypt(socialUser.getAccessToken()), decrypt(socialUser.getSecret()), decrypt(socialUser.getRefreshToken()), expireTime(socialUser.getExpireTime().getTime()));
//        }
//
//        private String decrypt(String encryptedText) {
//            return encryptedText != null ? textEncryptor.decrypt(encryptedText) : encryptedText;
//        }
//
//        private Long expireTime(Long expireTime) {
//            return expireTime == null || expireTime == 0 ? null : expireTime;
//        }
//
//    }
}
