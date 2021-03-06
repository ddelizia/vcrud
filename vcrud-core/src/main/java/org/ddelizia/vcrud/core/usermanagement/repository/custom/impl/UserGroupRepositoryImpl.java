package org.ddelizia.vcrud.core.usermanagement.repository.custom.impl;

import org.ddelizia.vcrud.core.basic.repository.AbstractCustomRepository;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup_;
import org.ddelizia.vcrud.core.usermanagement.repository.custom.UserGroupRepositoryCustom;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/02/14
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class UserGroupRepositoryImpl extends AbstractCustomRepository implements UserGroupRepositoryCustom{

    @Override
    public boolean findUserBelongsToGroup(User user, UserGroup userGroup) {
	    Collection<UserGroup> collection = getMongoTemplate().find(Query.query(
			    new Criteria().orOperator(
					    Criteria.where("id").is(userGroup.getId()),
					    Criteria.where("father").is(userGroup),
					    Criteria.where("anchestors").in(userGroup)
			    )
	    ), UserGroup.class);

        User u = getMongoTemplate().findOne(Query.query(
		        Criteria.where("userGroups").in(collection)
        ), User.class);

        return u!=null;
    }

}
