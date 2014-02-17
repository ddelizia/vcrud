package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;

public interface EmailAuthenticationServiceGateway {

    public void sendVerificationToken(EmailServiceTokenData model);

}
