package org.ddelizia.vcrud.core.mail.service;

import org.ddelizia.vcrud.core.mail.data.EmailDetail;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public interface EmailService {

    public static final String DEFAULT_BEAN_NAME = "defaultEmailService";

    public void sendEmail(final EmailDetail emailDetail, final Object data, final String view, final Map<String, String> resources);

}
