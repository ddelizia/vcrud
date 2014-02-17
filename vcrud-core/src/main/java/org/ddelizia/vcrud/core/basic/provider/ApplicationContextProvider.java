package org.ddelizia.vcrud.core.basic.provider;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
@Configuration("VcrudApplicationContextProvider")
public class ApplicationContextProvider implements ApplicationContextAware, BeanFactoryPostProcessor {

    private static ApplicationContext ctx = null;

    private static ConfigurableListableBeanFactory factory;

    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public static <T extends Object> T getBean(String beanName, Class<T> beanClass){
        if (StringUtils.isEmpty(beanName)){
            return  getApplicationContext().getBean(beanClass);
        }
        return getApplicationContext().getBean(beanName, beanClass);
    }

    public static <T extends Object> T getBean(Class<T> beanClass){
        return  getApplicationContext().getBean(beanClass);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.factory = configurableListableBeanFactory;
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return factory;
    }
}
