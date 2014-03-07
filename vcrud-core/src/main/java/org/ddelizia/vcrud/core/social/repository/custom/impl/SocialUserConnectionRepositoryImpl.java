package org.ddelizia.vcrud.core.social.repository.custom.impl;

import org.ddelizia.vcrud.core.basic.repository.AbstractCustomRepository;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.custom.SocialUserConnectionRepositoryCustom;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class SocialUserConnectionRepositoryImpl extends AbstractCustomRepository implements SocialUserConnectionRepositoryCustom{

    private Class<SocialUserConnection> clazz = SocialUserConnection.class;

    //TODo finish this
    @Override
    public SocialUserConnection findByUserNameAndProviderIdMaxRank(String providerId, String providerUserId) {
        getMongoTemplate().find(Query.query(Criteria.where("test").is("")), clazz);
        return null;
    }

    //TODO implementing this method
    @Override
    public List<SocialUserConnection> findByUserNameInProviderUsers(String userId, MultiValueMap<String, String> providerUsers) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    //TODO implementing this method
    @Override
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


}