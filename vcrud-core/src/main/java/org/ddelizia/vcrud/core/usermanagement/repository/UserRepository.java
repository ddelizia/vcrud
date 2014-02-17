package org.ddelizia.vcrud.core.usermanagement.repository;

import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 10:06
 * To change this template use File | Settings | File Templates.
 */
public interface UserRepository extends MongoRepository<User,String>{

    public User findByName(String name);

    public User findByEmail (String email);

}
