package org.ddelizia.vcrud.core.repository;

import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.model.usermanagement.VcrudPrincipal_;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, Long>{

    public VerificationToken findByUuid(String uuid);

    public VerificationToken findByToken(String token);
}
