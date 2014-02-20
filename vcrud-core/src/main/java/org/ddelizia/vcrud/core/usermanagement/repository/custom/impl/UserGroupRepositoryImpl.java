package org.ddelizia.vcrud.core.usermanagement.repository.custom.impl;

import org.ddelizia.vcrud.core.basic.repository.AbstractCustomRepository;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.custom.UserGroupRepositoryCustom;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
        User u = getMongoTemplate().findOne(Query.query(
                Criteria.where("id").is(user.getId()).and("userGroups").elemMatch(
                        Criteria.where("id").is(userGroup.getId())
                )
        ), User.class);

        return u!=null;
    }

}
