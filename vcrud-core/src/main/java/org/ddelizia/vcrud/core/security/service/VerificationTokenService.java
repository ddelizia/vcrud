package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.usermanagement.model.User;

/**
 * @author ddelizia
 * @since 17/02/14 15:19
 */
public interface VerificationTokenService {

    public static final String DEFAULT_BEAN_NAME = "VerificationTokenService";

    public VerificationToken getActiveToken(VerificationToken.VerificationTokenType tokenType, User user);

}
