package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.core.usermanagement.model.User;

/**
 * @author ddelizia
 * @since 17/02/14 15:42
 */
public interface CryptoService {

    public static final String DEFAULT_BEAN_NAME = "CryptoService";

    /**
     * Hash the password using salt values
     * See https://www.owasp.org/index.php/Hashing_Java
     *
     * @param passwordToHash
     * @param user
     * @return hashed password
     */
    public String hashPassword(String passwordToHash, User user) throws Exception ;

    public byte[] getHash(int numberOfIterations, String password, byte[] salt) throws Exception;


}
