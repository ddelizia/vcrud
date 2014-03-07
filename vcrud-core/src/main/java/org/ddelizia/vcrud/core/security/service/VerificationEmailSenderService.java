package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public interface VerificationEmailSenderService {

    public static final String DEFAULT_BEAN_NAME = "defaultAuthenticationEmailSenderService";

    public EmailServiceTokenData sendVerificationEmail(EmailServiceTokenData emailServiceTokenModel);

    public EmailServiceTokenData sendRegistrationEmail(EmailServiceTokenData emailServiceTokenModel);

    public EmailServiceTokenData sendLostPasswordEmail(EmailServiceTokenData emailServiceTokenModel);
}
