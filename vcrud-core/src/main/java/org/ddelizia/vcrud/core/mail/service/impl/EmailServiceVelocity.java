package org.ddelizia.vcrud.core.mail.service.impl;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.ddelizia.vcrud.core.mail.data.EmailDetail;
import org.ddelizia.vcrud.core.mail.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */

@Service(EmailService.DEFAULT_BEAN_NAME)
public class EmailServiceVelocity implements EmailService {

    private static Logger LOG = Logger.getLogger(EmailServiceVelocity.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VelocityEngine velocityEngine;


    @Override
    public void sendEmail(final EmailDetail emailDetail, final Object data, final String view, final Map<String, String> resources ){
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_RELATED, "UTF-8");
                messageHelper.setTo(emailDetail.getTo());
                messageHelper.setFrom(emailDetail.getFrom());
                messageHelper.setReplyTo(emailDetail.getReplyTo());
                messageHelper.setSubject(emailDetail.getSubject());
                Map model = new HashMap();
                model.put("model", data);
                String text = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, view, model);
                messageHelper.setText(new String(text.getBytes(), "UTF-8"), true);
                for(String resourceIdentifier: resources.keySet()) {
                    addInlineResource(messageHelper, resources.get(resourceIdentifier), resourceIdentifier);
                }
            }
        };

        this.mailSender.send(preparator);
    }

    private void addInlineResource(MimeMessageHelper messageHelper, String resourcePath, String resourceIdentifier) throws MessagingException {
        Resource resource = new ClassPathResource(resourcePath);
        messageHelper.addInline(resourceIdentifier, resource);
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
}
