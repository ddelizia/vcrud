package org.ddelizia.vcrud.email.service;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:17
 * To change this template use File | Settings | File Templates.
 */
public interface SmtpMailServerService {

    public void startSmtpServer();

    public void stopSmtpServer();
}
