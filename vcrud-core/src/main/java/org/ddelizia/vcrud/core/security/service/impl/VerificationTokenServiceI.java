package org.ddelizia.vcrud.core.security.service.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.security.repository.VerificationTokenRepository;
import org.ddelizia.vcrud.core.security.service.VerificationTokenService;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ddelizia
 * @since 17/02/14 15:20
 */
@Service(VerificationTokenService.DEFAULT_BEAN_NAME)
public class VerificationTokenServiceI implements VerificationTokenService{

    private static final Logger LOGGER = Logger.getLogger(VerificationTokenServiceI.class);
    
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    public VerificationToken getActiveToken(VerificationToken.VerificationTokenType tokenType, User user) {
        VerificationToken activeToken = null;
        for (VerificationToken token : verificationTokenRepository.findByUser(user)) {
            if (token.getTokenType().equals(tokenType)
                    && !token.hasExpired() && !token.isVerified()) {
                activeToken = token;
                break;
            }
        }
        return activeToken;
    }

    public void setVerificationTokenRepository(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }
}
