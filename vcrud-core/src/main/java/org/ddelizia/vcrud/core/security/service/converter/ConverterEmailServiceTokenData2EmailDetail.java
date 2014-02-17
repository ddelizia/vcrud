package org.ddelizia.vcrud.core.security.service.converter;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.mail.data.EmailDetail;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author ddelizia
 * @since 16/02/14 12:07
 */
@Component
public class ConverterEmailServiceTokenData2EmailDetail implements Converter<EmailServiceTokenData, EmailDetail>{

    private static final Logger LOGGER = Logger.getLogger(ConverterEmailServiceTokenData2EmailDetail.class);

    @Autowired
    private AppConfig appConfig;

    /**
     * Convert the source of type S to target type T.
     *
     * @param source the source object to convert, which must be an instance of S
     * @return the converted object, which must be an instance of T
     * @throws IllegalArgumentException if the source could not be converted to the desired target type
     */
    @Override
    public EmailDetail convert(EmailServiceTokenData source) {
        EmailDetail emailDetail = new EmailDetail();
        emailDetail.setTo(source.getEmailAddress());
        emailDetail.setFrom(appConfig.getProperty(
                AppConfig.EMAIL_SERVICES_FROM_ADDRESS, String.class, null
        ));
        emailDetail.setReplyTo(appConfig.getProperty(
                AppConfig.EMAIL_SERVICES_REPLYTO_ADDRESS, String.class, null
        ));
        return emailDetail;
    }

    public AppConfig getAppConfig() {
        return appConfig;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
