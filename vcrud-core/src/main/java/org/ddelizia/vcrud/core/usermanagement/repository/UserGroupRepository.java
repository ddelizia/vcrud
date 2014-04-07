package org.ddelizia.vcrud.core.usermanagement.repository;

import org.ddelizia.vcrud.core.transaction.Retry;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.custom.UserGroupRepositoryCustom;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/02/14
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */

public interface UserGroupRepository extends MongoRepository<UserGroup, String>, UserGroupRepositoryCustom{

    public UserGroup findByGroupName(String groupName);

	@Retry(on = OptimisticLockingFailureException.class, times = 10)
    public List<UserGroup> findByFather(UserGroup father);

}
