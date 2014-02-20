package org.ddelizia.vcrud.core.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 14:23
 * To change this template use File | Settings | File Templates.
 */
@Configuration("appConfig")
public class AppConfig {

    private static Logger LOGGER = Logger.getLogger(AppConfig.class);

    public final static String HOSTNAME_PROPERTY =                                 "hostNameUrl";

    public final static String SECURITY_AUTHORIZATION_REQUIRE_SIGNED_REQUESTS =    "security.authorization.requireSignedRequests";
    public final static String SESSION_EXPIRY_DURATION =                           "session.timeToLive.inMinutes";
    public final static String SESSION_DATE_OFFSET_IN_MINUTES =                    "session.date.offset.inMinutes";
    public final static String TOKEN_EMAIL_REGISTRATION_DURATION =                 "token.emailRegistration.timeToLive.inMinutes";
    public final static String TOKEN_EMAIL_VERIFICATION_DURATION =                 "token.emailVerification.timeToLive.inMinutes";
    public final static String TOKEN_LOST_PASSWORD_DURATION =                      "token.lostPassword.timeToLive.inMinutes";
    public final static String EMAIL_SERVICES_FROM_ADDRESS =                       "email.services.fromAddress";
    public final static String EMAIL_SERVICES_REPLYTO_ADDRESS =                    "email.services.replyTo";
    public final static String EMAIL_SERVICES_VERIFICATION_EMAIL_SUBJECT_TEXT =    "email.services.emailVerificationSubjectText";
    public final static String EMAIL_SERVICES_REGISTRATION_EMAIL_SUBJECT_TEXT =    "email.services.emailRegistrationSubjectText";
    public final static String EMAIL_SERVICES_LOST_PASSWORD_SUBJECT_TEXT =         "email.services.lostPasswordSubjectText";
    public final static String USER_USERGROUP_REST =                               "user.usergroup.rest";
    public final static String USER_USERGROUP_ADMIN =                              "user.usergroup.admin";
    public final static String USER_USERGROUP_SOCIAL =                             "user.usergroup.social";
    public final static String USER_USERGROUP_AUTHENTICATED =                      "user.usergroup.authenticated";
    public final static String HASH_SALT =                                         "crypto.hash.salt";
    public final static String HASH_ITERATIONS =                                   "crypto.hash.iterations";
    public final static String SOCIAL_FACEBOOK_CLIENT_ID =                         "facebook.clientId";
    public final static String SOCIAL_FACEBOOK_CLIENT_SECRET =                     "facebook.clientSecret";


    @Autowired
    protected org.springframework.core.env.Environment environment;


    public Environment getEnvironment() {
        return environment;
    }

    public <T extends Object> T getProperty(String property, Class<T> clazz, String defaultValue){
        String theProperty = environment.getProperty(property, defaultValue);
        if (!StringUtils.isEmpty(theProperty)){
            try {
                Constructor<T> constructor = clazz.getConstructor(String.class);
                return constructor.newInstance(theProperty);
            } catch (NoSuchMethodException e) {
                LOGGER.error("NoSuchMethodException: Could not instantiate the class cause "+
                        clazz.getName()+
                        " attribute has not a string constructor");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                LOGGER.error("InvocationTargetException: Could not instantiate the class cause "+
                        clazz.getName()+
                        " attribute has not a string constructor");
                e.printStackTrace();
            } catch (InstantiationException e) {
                LOGGER.error("InstantiationException: Could not instantiate the class cause "+
                        clazz.getName()+
                        " attribute has not a string constructor");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                LOGGER.error("IllegalAccessException: Could not instantiate the class cause "+
                        clazz.getName()+
                        " attribute has not a string constructor");
                e.printStackTrace();
            }
        }
        throw new RuntimeException("Error in AppConfig.getProperty");
    }
}
