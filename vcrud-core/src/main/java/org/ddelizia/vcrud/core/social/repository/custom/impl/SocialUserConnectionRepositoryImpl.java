package org.ddelizia.vcrud.core.social.repository.custom.impl;

import org.bson.types.ObjectId;
import org.ddelizia.vcrud.core.basic.repository.AbstractCustomRepository;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.custom.SocialUserConnectionRepositoryCustom;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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

    @Override
    public SocialUserConnection findByUserNameAndProviderIdMaxRank(String userId, String providerId) {
	    Query query = Query.query(
			    new Criteria().andOperator(
					    Criteria.where("user.$id").is(new ObjectId(userId)),
					    Criteria.where("providerId").is(providerId)
			    )
	    );
	    query.with(new Sort(Sort.Direction.DESC,"rank"));
	    query.limit(1);
	    return  getMongoTemplate().findOne(query, clazz);
    }

    @Override
    public List<SocialUserConnection> findByUserIdInProviderUsers(String userId, MultiValueMap<String, String> providerUsers) {
	    if (providerUsers == null || providerUsers.isEmpty()) {
		    throw new IllegalArgumentException("Unable to execute find: no providerUsers provided");
	    }

	    ArrayList<Criteria> orCriterias = new ArrayList<>();
	    for (Iterator<Map.Entry<String, List<String>>> it = providerUsers.entrySet().iterator(); it.hasNext();) {
		    Map.Entry<String, List<String>> entry = it.next();
		    String providerId = entry.getKey();

		    Criteria rootCriteria = new Criteria();
		    rootCriteria.andOperator(
				    Criteria.where("providerId").is(providerId),
				    Criteria.where("providerUserId").in(entry.getValue())
		    );
		    orCriterias.add(rootCriteria);
	    }

	    Query query = Query.query(
			    new Criteria().andOperator(
					    Criteria.where("user.$id").is(new ObjectId(userId)),
					    new Criteria().orOperator(
							    orCriterias.toArray(new Criteria[orCriterias.size()])
					    )
			    )
	    );
	    return  getMongoTemplate().find(query, clazz);

    }

    @Override
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
	    Query query = Query.query(
			    new Criteria().andOperator(
					    Criteria.where("providerId").is(providerId),
					    Criteria.where("providerUserId").in(providerUserIds)
			    )
	    );
	    List<SocialUserConnection> list =  getMongoTemplate().find(query, clazz);
	    Set<String> userIds = new HashSet<>();
	    for (SocialUserConnection socialUserConnection: list){
		    userIds.add(socialUserConnection.getUser().getId());
	    }
	    return userIds;
    }

	@Override
	public List<SocialUserConnection> findByUserId(String userId){
		return  getMongoTemplate().find(Query.query(
				Criteria.where("user.$id").is(new ObjectId(userId))
		), clazz);
	}

	@Override
	public List<SocialUserConnection> findByUserIdAndProviderId(String userId, String providerId){
		return  getMongoTemplate().find(Query.query(
				new Criteria().andOperator(
						Criteria.where("user.$id").is(new ObjectId(userId)),
						Criteria.where("providerId").is(providerId)
				)
		), clazz);
	}

	@Override
	public SocialUserConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId){
		return  getMongoTemplate().findOne(Query.query(
				new Criteria().andOperator(
						Criteria.where("user.$id").is(new ObjectId(userId)),
						Criteria.where("providerId").is(providerId),
						Criteria.where("providerUserId").is(providerUserId)
				)
		), clazz);
	}


}
