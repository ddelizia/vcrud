package org.ddelizia.vcrud.core.security.repository;

import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String>{

    public VerificationToken findByToken(String token);

    public List<VerificationToken> findByUser(User user);

}
