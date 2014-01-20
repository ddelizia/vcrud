package org.ddelizia.vcrud.test.config;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.Log4jConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
@Configuration
@PropertySource({"classpath:/META-INF/default-test.properties", "classpath:/META-INF/moduletest.properties"})
public class PropertyConfig
{
    static @Bean
    public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    static @Bean
    public MethodInvokingFactoryBean defaultMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(Log4jConfigurer.class);
        methodInvokingFactoryBean.setTargetMethod("initLogging");
        methodInvokingFactoryBean.setArguments(new Object[]{"classpath:META-INF/moduletest.properties"});
        return methodInvokingFactoryBean;
    }

    static @Bean
    public MethodInvokingFactoryBean basicMethodInvokingFactoryBean(){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(Log4jConfigurer.class);
        methodInvokingFactoryBean.setTargetMethod("initLogging");
        methodInvokingFactoryBean.setArguments(new Object[]{"classpath:META-INF/default-test.properties"});
        return methodInvokingFactoryBean;
    }

}
