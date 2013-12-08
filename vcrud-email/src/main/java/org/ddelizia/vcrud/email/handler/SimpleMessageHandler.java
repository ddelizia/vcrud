package org.ddelizia.vcrud.email.handler;

import org.apache.log4j.Logger;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.RejectException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:25
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMessageHandler implements org.subethamail.smtp.MessageHandler {

    private static final Logger LOGGER = Logger.getLogger(SimpleMessageHandler.class);

    private MessageContext ctx;

    public SimpleMessageHandler(MessageContext ctx) {
        this.ctx = ctx;
    }

    public void from(String from) throws RejectException {
        LOGGER.debug("FROM: " + from);
    }

    public void recipient(String recipient) throws RejectException {
        LOGGER.debug("RECIPIENT: " + recipient);
    }

    public void data(InputStream data) throws IOException {
        LOGGER.debug("MAIL DATA");
        LOGGER.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
        LOGGER.debug(this.convertStreamToString(data));
        LOGGER.debug("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =");
    }

    public void done() {
        LOGGER.debug("Finished");
    }

    public String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
