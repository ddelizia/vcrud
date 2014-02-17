package org.ddelizia.vcrud.core.config;

import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.util.Log4jConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 16:57
 * To change this template use File | Settings | File Templates.
 */
@Configuration("propertyConfig")
@PropertySource({"classpath:/META-INF/properties/common.properties"})
public class PropertyConfig {

    static @Bean
    public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer()
    {
        initLog4jProperties("classpath:/META-INF/properties/common.properties");
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Properties to support the 'test' mode of operation.
     */
    @Configuration
    @Profile("test")
    @PropertySource("classpath:/META-INF/properties/test.properties")
    static class PropertyConfigTest
    {
        /**
         * Adding more configuration
         */
        public PropertyConfigTest (){
            initLog4jProperties("classpath:/META-INF/properties/test.properties");
        }
    }

    /**
     * Properties to support the 'dev' mode of operation.
     */
    @Configuration
    @Profile("dev")
    @PropertySource("classpath:/META-INF/properties/dev.properties")
    static class PropertyConfigDev
    {
        /**
         * Adding more configuration
         */
        public PropertyConfigDev (){
            initLog4jProperties("classpath:/META-INF/properties/dev.properties");
        }
    }

    /**
     * Properties to support the 'uat' mode of operation.
     */
    @Configuration
    @Profile("uat")
    @PropertySource("classpath:/META-INF/properties/uat.properties")
    static class PropertyConfigUat
    {
        /**
         * Adding more configuration
         */
        public PropertyConfigUat (){
            initLog4jProperties("classpath:/META-INF/properties/uat.properties");
        }
    }

    /**
     * Properties to support the 'prod' mode of operation.
     */
    @Configuration
    @Profile("prod")
    @PropertySource("classpath:/META-INF/properties/prod.properties")
    static class PropertyConfigProd
    {
        /**
         * Adding more configuration
         */
        public PropertyConfigProd (){
            initLog4jProperties("classpath:/META-INF/properties/prod.properties");
        }
    }

    /**
     * Init log 4j
     * @param properties
     */
    public static void initLog4jProperties(Object ... properties){
        MethodInvokingFactoryBean methodInvokingFactoryBean = new MethodInvokingFactoryBean();
        methodInvokingFactoryBean.setTargetClass(Log4jConfigurer.class);
        methodInvokingFactoryBean.setTargetMethod("initLogging");
        methodInvokingFactoryBean.setArguments(properties);
    }


}
