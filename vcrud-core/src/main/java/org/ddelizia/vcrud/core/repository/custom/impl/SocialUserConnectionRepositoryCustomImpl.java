package org.ddelizia.vcrud.core.repository.custom.impl;

import org.ddelizia.vcrud.core.repository.custom.SocialUserConnectionRepositoryCustom;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 12:23
 * To change this template use File | Settings | File Templates.
 */
public class SocialUserConnectionRepositoryCustomImpl implements SocialUserConnectionRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    private Class<SocialUserConnection> clazz = SocialUserConnection.class;

    //TODo finish this
    @Override
    public SocialUserConnection findByUserNameAndProviderIdMaxRank(String providerId, String providerUserId) {
        mongoTemplate.find(Query.query(Criteria.where("test").is("")), SocialUserConnection.class);
        return null;
    }

    //TODO implementing this method
    @Override
    public List<SocialUserConnection> findByUserNameInProviderUsers(String userId, MultiValueMap<String, String> providerUsers) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
