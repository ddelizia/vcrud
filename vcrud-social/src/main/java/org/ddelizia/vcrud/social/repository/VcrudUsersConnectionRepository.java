package org.ddelizia.vcrud.social.repository;

import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.social.SocialUser_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.social.dao.SocialUserDao;
import org.ddelizia.vcrud.social.model.SocialUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.*;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/11/13
 * Time: 1:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class VcrudUsersConnectionRepository implements UsersConnectionRepository{

    @Autowired
    private ModelService modelService;
    
    @Autowired
    private SocialUserDao socialUserDao;

    private final ConnectionFactoryLocator connectionFactoryLocator;

    private final TextEncryptor textEncryptor;

    private ConnectionSignUp connectionSignUp;

    public VcrudUsersConnectionRepository( ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
        this.connectionFactoryLocator = connectionFactoryLocator;
        this.textEncryptor = textEncryptor;
    }

    @Override
    public List<String> findUserIdsWithConnection(Connection<?> connection) {
        List<String> usrs = new ArrayList<String>();
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(SocialUser_.providerId.getName(), connection.getKey().getProviderId());
        map.put(SocialUser_.providerUserId.getName(), connection.getKey().getProviderUserId());
        List<SocialUser> socialUsers = modelService.getModels(map, SocialUser.class, null, null);
        if (!socialUsers.isEmpty()){
            for (SocialUser socialUser: socialUsers){

                usrs.add(getUserId(socialUser.getUser()));
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


        return usrs;
    }

    @Override
    public Set<String> findUserIdsConnectedTo(String s, Set<String> strings) {
        return socialUserDao.findUsersConnectedTo(s, strings);
    }

    @Override
    public ConnectionRepository createConnectionRepository(String s) {
        if (s == null) {
            throw new IllegalArgumentException("userId cannot be null");
        }
        return new VcrudConnectionRepository(s, connectionFactoryLocator, textEncryptor);
    }

    public ConnectionSignUp getConnectionSignUp() {
        return connectionSignUp;
    }

    public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
        this.connectionSignUp = connectionSignUp;
    }

    private String getUserId(User user){
        return user.getEmail();
    }
}
