package org.ddelizia.vcrud.model.multitenancy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 11:48
 * To change this template use File | Settings | File Templates.
 */
public class TenantContextWrapper implements ApplicationContextAware,
        BeanFactoryPostProcessor {

    private ApplicationContext appContext;
    private ConfigurableListableBeanFactory factory;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory)
            throws BeansException{
        this.factory = factory;
    }
    public void setApplicationContext(ApplicationContext c)
            throws BeansException {
        this.appContext = c;
    }


    public ApplicationContext getAppContext() {
        return appContext;
    }

    public void setAppContext(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    public ConfigurableListableBeanFactory getFactory() {
        return factory;
    }

    public void setFactory(ConfigurableListableBeanFactory factory) {
        this.factory = factory;
    }
}
