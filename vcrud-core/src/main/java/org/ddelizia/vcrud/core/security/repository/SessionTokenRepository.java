package org.ddelizia.vcrud.core.security.repository;

import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
public interface SessionTokenRepository extends MongoRepository<SessionToken, String>{

    public List<SessionToken> findByUser(User user);

    public SessionToken findByToken(String token);

}
