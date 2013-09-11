package org.ddelizia.vcrud.core.test.model;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 14:29
 * To change this template use File | Settings | File Templates.
 */
@Configuration("org.ddelizia.vcrud.gui7.config.PropertyConfig")
@PropertySource({"classpath:/META-INF/common.properties"})
public class PropertyConfig
{
    static @Bean
    public PropertySourcesPlaceholderConfigurer myPropertySourcesPlaceholderConfigurer()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Properties to support the 'test' mode of operation.
     */
    @Configuration
    @Profile("vcrud-test")
    @PropertySource("classpath:/META-INF/vcrud-test.properties")
    static class Test
    {
        /**
         * Adding more configuration
         */
        public Test (){
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(Level.DEBUG);
        }
    }

    /**
     * Properties to support the 'production' mode of operation.
     */
    @Configuration
    @Profile("vcrud-production")
    @PropertySource("classpath:/META-INF/vcrud-production.properties")
    static class Production
    {
        /**
         * Adding more configuration
         */
        public Production(){
            Logger rootLogger = Logger.getRootLogger();
            rootLogger.setLevel(Level.INFO);
            RollingFileAppender fileAppender = new RollingFileAppender();
            fileAppender.setName("File_Appender");
            fileAppender.setFile("mioLog.log");
            fileAppender.setMaxFileSize("100MB");
            fileAppender.setMaxBackupIndex(10);
            fileAppender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %p [%C:%L] - %m%n"));
            fileAppender.activateOptions();
            rootLogger.addAppender(fileAppender);
        }
    }
}
