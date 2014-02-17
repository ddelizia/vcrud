package org.ddelizia.vcrud.core.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractEmailSenderService {

    @Autowired
    @Qualifier(EmailService.DEFAULT_BEAN_NAME)
    private EmailService emailService;

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }
}
