package org.ddelizia.vcrud.email.service.impl;

import org.apache.commons.lang.StringUtils;
import org.ddelizia.vcrud.email.service.SmtpMailServerService;
import org.springframework.beans.factory.annotation.Required;
import org.subethamail.smtp.AuthenticationHandlerFactory;
import org.subethamail.smtp.MessageHandlerFactory;
import org.subethamail.smtp.server.SMTPServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:18
 * To change this template use File | Settings | File Templates.
 */
public class SmtpMailServerServiceImpl implements SmtpMailServerService{

    private SMTPServer smtpServer;

    private String hostname;

    private Integer port;

    private String username;

    private String password;

    private MessageHandlerFactory messageHandlerFactory;

    private AuthenticationHandlerFactory authenticationHandlerFactory;


    private void configureServer(){
        smtpServer = new SMTPServer(messageHandlerFactory);
        smtpServer.setHostName(hostname);
        smtpServer.setPort(port);
        if (authenticationHandlerFactory != null){
            smtpServer.setAuthenticationHandlerFactory(authenticationHandlerFactory);
        }
    }

    @Override
    public void startSmtpServer(){
        configureServer();
        smtpServer.start();
    }

    @Override
    public void stopSmtpServer(){
        smtpServer.stop();
    }


    public String getHostname() {
        return hostname;
    }

    @Required
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    @Required
    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public MessageHandlerFactory getMessageHandlerFactory() {
        return messageHandlerFactory;
    }

    public void setMessageHandlerFactory(MessageHandlerFactory messageHandlerFactory) {
        this.messageHandlerFactory = messageHandlerFactory;
    }

    public AuthenticationHandlerFactory getAuthenticationHandlerFactory() {
        return authenticationHandlerFactory;
    }

    public void setAuthenticationHandlerFactory(AuthenticationHandlerFactory authenticationHandlerFactory) {
        this.authenticationHandlerFactory = authenticationHandlerFactory;
    }
}
