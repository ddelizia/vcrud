package org.ddelizia.vcrud.email.factory;

import org.ddelizia.vcrud.email.handler.SimpleMessageHandler;
import org.subethamail.smtp.MessageContext;
import org.subethamail.smtp.MessageHandler;
import org.subethamail.smtp.MessageHandlerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/10/13
 * Time: 09:31
 * To change this template use File | Settings | File Templates.
 */
public class SimpleMessageHandlerFactory implements MessageHandlerFactory{
    @Override
    public MessageHandler create(MessageContext messageContext) {
        return new SimpleMessageHandler(messageContext);
    }
}
