package org.ddelizia.vcrud.email.service;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/14/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
public interface MailService {

    /**
     * Simple send email
     * @param fromAddress
     * @param toAddress
     * @param subject
     * @param text
     */
    public void sendEmail(String fromAddress, String toAddress, String subject, String text);
}
