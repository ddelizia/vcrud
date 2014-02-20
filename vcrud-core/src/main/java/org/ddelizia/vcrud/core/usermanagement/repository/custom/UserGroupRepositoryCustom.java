package org.ddelizia.vcrud.core.usermanagement.repository.custom;

import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/02/14
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */

public interface UserGroupRepositoryCustom {

    public boolean findUserBelongsToGroup(User user, UserGroup userGroup);

}
