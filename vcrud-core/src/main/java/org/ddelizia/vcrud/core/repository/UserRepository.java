package org.ddelizia.vcrud.core.repository;

import org.ddelizia.vcrud.model.usermanagement.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends MongoRepository<User,Long>{

    public User findByName(String name);

    public User findByEmail (String email);
}
