package org.ddelizia.vcrud.core.social;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserConnectionRepository extends MongoRepository<SocialUserConnection,Long>{

    public List<SocialUserConnection> findIn(String userId, MultiValueMap<String, String> providerUsers);

}
