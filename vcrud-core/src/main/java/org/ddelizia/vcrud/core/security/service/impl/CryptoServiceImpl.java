package org.ddelizia.vcrud.core.security.service.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.service.CryptoService;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

/**
 * @author ddelizia
 * @since 17/02/14 15:43
 */

@Service(CryptoService.DEFAULT_BEAN_NAME)
public class CryptoServiceImpl implements CryptoService{

    private static final Logger LOGGER = Logger.getLogger(CryptoServiceImpl.class);

    @Autowired
    private AppConfig appConfig;

    public String hashPassword(String passwordToHash, User user) throws Exception {
        return hashToken(passwordToHash, user.getId() +
            appConfig.getProperty(AppConfig.HASH_SALT, String.class, null)
        );
    }


    private String hashToken(String token, String salt) throws Exception {
        byte [] result = getHash(appConfig.getProperty(AppConfig.HASH_ITERATIONS, Integer.class, null), token, salt.getBytes());

        return new String(Base64.encode(result));
    }

    public byte[] getHash(int numberOfIterations, String password, byte[] salt) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.reset();
        digest.update(salt);
        byte[] input = digest.digest(password.getBytes("UTF-8"));
        for (int i = 0; i < numberOfIterations; i++) {
            digest.reset();
            input = digest.digest(input);
        }
        return input;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
