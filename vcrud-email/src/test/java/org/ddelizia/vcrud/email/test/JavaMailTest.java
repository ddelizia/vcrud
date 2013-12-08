package org.ddelizia.vcrud.email.test;

import org.ddelizia.vcrud.email.service.MailService;
import org.ddelizia.vcrud.email.service.SmtpMailServerService;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:03
 * To change this template use File | Settings | File Templates.
 */
public class JavaMailTest extends AbstractJunit4Vcrud{

    @Autowired
    private MailService mailService;

    @Autowired
    private SmtpMailServerService smtpMailServerService;

    @Override
    public void vcrudBefore() {
    }

    @Test
    public void testSendMail(){

        mailService.sendEmail("danilodelizia@yahoo.it","ddelizia@gmail.com","This is a test", "This is the content of the email");

    }
}
