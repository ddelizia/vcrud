package org.ddelizia.vcrud.core.security.service.impl;

import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.mail.data.EmailDetail;
import org.ddelizia.vcrud.core.mail.service.AbstractEmailSenderService;
import org.ddelizia.vcrud.core.mail.service.EmailService;
import org.ddelizia.vcrud.core.security.service.VerificationEmailSenderService;
import org.ddelizia.vcrud.core.security.service.converter.ConverterEmailServiceTokenData2EmailDetail;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 18:59
 * To change this template use File | Settings | File Templates.
 */

@Service(VerificationEmailSenderService.DEFAULT_BEAN_NAME)
public class VerificationEmailSenderServiceImpl extends AbstractEmailSenderService implements VerificationEmailSenderService {

    @Autowired
    @Qualifier(EmailService.DEFAULT_BEAN_NAME)
    private EmailService emailService;

    @Autowired
    private ConverterEmailServiceTokenData2EmailDetail converterEmailServiceTokenData2EmailDetail;

    @Autowired
    private AppConfig appConfig;


    @Override
    public EmailServiceTokenData sendVerificationEmail(EmailServiceTokenData emailServiceTokenModel) {
        Map<String, String> resources = new HashMap<>();
        EmailDetail emailDetail = converterEmailServiceTokenData2EmailDetail.convert(emailServiceTokenModel);
        emailDetail.setSubject(appConfig.getProperty(
                AppConfig.EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT, String.class, null
        ));
        emailService.sendEmail(
                emailDetail,
                emailServiceTokenModel,
                "/META-INF/email/template/authentication/VerifyMail.vm",
                resources);
        return emailServiceTokenModel;
    }

    @Override
    public EmailServiceTokenData sendRegistrationEmail(EmailServiceTokenData emailServiceTokenModel) {
        Map<String, String> resources = new HashMap<>();
        EmailDetail emailDetail = converterEmailServiceTokenData2EmailDetail.convert(emailServiceTokenModel);
        emailDetail.setSubject(appConfig.getProperty(
                AppConfig.EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT, String.class, null
        ));
        emailService.sendEmail(
                emailDetail,
                emailServiceTokenModel,
                "/META-INF/email/template/authentication/RegistrationEmail.vm",
                resources);
        return emailServiceTokenModel;
    }

    @Override
    public EmailServiceTokenData sendLostPasswordEmail(EmailServiceTokenData emailServiceTokenModel) {
        Map<String, String> resources = new HashMap<>();
        EmailDetail emailDetail = converterEmailServiceTokenData2EmailDetail.convert(emailServiceTokenModel);
        emailDetail.setSubject(appConfig.getProperty(
                AppConfig.EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT, String.class, null
        ));
        emailService.sendEmail(
                emailDetail,
                emailServiceTokenModel,
                "/META-INF/email/template/authentication/LostPasswordEmail.vm",
                resources);
        return emailServiceTokenModel;
    }

    public EmailService getEmailService() {
        return emailService;
    }

    public void setEmailService(EmailService emailService) {
        this.emailService = emailService;
    }

    public ConverterEmailServiceTokenData2EmailDetail getConverterEmailServiceTokenData2EmailDetail() {
        return converterEmailServiceTokenData2EmailDetail;
    }

    public void setConverterEmailServiceTokenData2EmailDetail(ConverterEmailServiceTokenData2EmailDetail converterEmailServiceTokenData2EmailDetail) {
        this.converterEmailServiceTokenData2EmailDetail = converterEmailServiceTokenData2EmailDetail;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
