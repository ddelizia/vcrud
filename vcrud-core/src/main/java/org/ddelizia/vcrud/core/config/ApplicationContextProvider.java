package org.ddelizia.vcrud.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
@Configuration("VcrudApplicationContextProvider")
public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = ctx;
    }
}
