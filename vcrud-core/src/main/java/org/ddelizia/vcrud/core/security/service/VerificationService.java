package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.commons.PasswordRequest;
import org.ddelizia.vcrud.commons.client.LostPasswordRequest;
import org.ddelizia.vcrud.core.security.model.VerificationToken;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 06/02/14
 * Time: 12:16
 * To change this template use File | Settings | File Templates.
 */
public interface VerificationService {

    public static final String DEFAULT_BEAN_NAME = "defaultVerificationService";

    public VerificationToken sendEmailVerificationToken(String userId);

    public VerificationToken sendEmailRegistrationToken(String userId);

    public VerificationToken sendLostPasswordToken(LostPasswordRequest lostPasswordRequest);

    public VerificationToken verify(String base64EncodedToken);

    public VerificationToken generateEmailVerificationToken(String emailAddress);

    public VerificationToken resetPassword(String base64EncodedToken, PasswordRequest passwordRequest);
}
