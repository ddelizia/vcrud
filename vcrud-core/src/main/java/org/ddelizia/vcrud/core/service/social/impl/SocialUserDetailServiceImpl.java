package org.ddelizia.vcrud.core.service.social.impl;

import org.ddelizia.vcrud.core.dao.UserDao;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.social.SocialUserDetailService;
import org.ddelizia.vcrud.core.service.social.VcrudConnectionRepository;
import org.ddelizia.vcrud.model.social.SocialUser;
import org.ddelizia.vcrud.model.social.SocialUser_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class SocialUserDetailServiceImpl implements SocialUserDetailService {

    @Autowired
    private ModelService modelService;

    @Autowired
    private UserDao userDao;

    private ConnectionSignUp connectionSignUp;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    public SocialUserDetailServiceImpl(ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        ConnectionKey key = connection.getKey();
        List<String> localUserIds = new LinkedList<String>();

        Map<String,Object> map = new HashMap<String, Object>();
        map.put(SocialUser_.providerId.getName(),key.getProviderId());
        map.put(SocialUser_.providerUserId.getName(),key.getProviderUserId());

        List<SocialUser> socialUsers = modelService.getModels(map, SocialUser.class, null, null);
        if (socialUsers.size() == 0 && connectionSignUp != null) {
            String newUserId = connectionSignUp.execute(connection);
            if (newUserId != null)
            {
                createConnectionRepository(newUserId).addConnection(connection);
                return Arrays.asList(newUserId);
            }
        }else{
            for(SocialUser socialUser:socialUsers){
                localUserIds.add(socialUser.getUser().getUsername());
            }
        }

        return localUserIds;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String s, Set<String> strings) {
        List<User> users = userDao.findUsersConnectedTo(s, strings);
        Set<String> localUserIds = new HashSet<String>();
        for(User user:users){
            localUserIds.add(user.getUsername());
        }
        return localUserIds;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ConnectionRepository createConnectionRepository(String userId) {
        if (userId == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new VcrudConnectionRepository(userId,connectionFactoryLocator,textEncryptor);
    }


    public ConnectionSignUp getConnectionSignUp() {
        return connectionSignUp;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }
}
