package org.ddelizia.vcrud.email.service.impl;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.ddelizia.vcrud.email.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.io.StringWriter;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/14/13
 * Time: 7:36 PM
 * To change this template use File | Settings | File Templates.
 */
@Service("vcrudMailSender")
public class MailServiceImpl implements MailService{

    @Autowired
    private MailSender mailSender;

    public void sendEmail(String fromAddress, String toAddress, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromAddress);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject(subject);

        RuntimeServices runtimeServices = RuntimeSingleton.getRuntimeServices();

        Velocity.init();

        VelocityContext context = new VelocityContext();
        context.put("name","ddelizia");
        context.put("project","vcrud");

        String s = "We are using $project $name to render this.";
        StringWriter w = new StringWriter();
        Velocity.evaluate( context, w, "mystring", s );

        simpleMailMessage.setText(w.toString());

        mailSender.send(simpleMailMessage);

    }
}
