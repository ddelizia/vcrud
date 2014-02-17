package org.ddelizia.vcrud.core.security.service.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.security.filter.AuthorizationRequestContext;
import org.ddelizia.vcrud.core.security.exception.AuthorizationException;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.repository.SessionTokenRepository;
import org.ddelizia.vcrud.core.security.service.AuthorizationService;
import org.ddelizia.vcrud.core.security.service.converter.ConverterUser2ExternalUser;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @author ddelizia
 * @since 16/02/14 11:49
 */
public class SessionTokenAuthorizationService implements AuthorizationService {

    private static final Logger LOGGER = Logger.getLogger(SessionTokenAuthorizationService.class);

    /**
     * directly access user objects
     */
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private SessionTokenRepository sessionTokenRepository;
    
    @Autowired
    private ConverterUser2ExternalUser converterUser2ExternalUser;

    @Autowired
    public SessionTokenAuthorizationService() {
    }

    public ExternalUser authorize(AuthorizationRequestContext securityContext) {
        String token = securityContext.getAuthorizationToken();
        ExternalUser externalUser = null;
        if(token == null) {
            return externalUser;
        }
        SessionToken currentSessionToken = sessionTokenRepository.findByToken(token);
        if (currentSessionToken == null){
            throw new AuthorizationException("Session token not found");
        }
        User user =  currentSessionToken.getUser();
        if(user == null) {
            throw new AuthorizationException("No user for thi session");
        }
        for (SessionToken sessionToken : sessionTokenRepository.findByUser(user)) {
            if (sessionToken.getToken().equals(token)) {
                sessionToken.setLastModification(new DateTime(new Date()));
                sessionTokenRepository.save(sessionToken);
                externalUser = converterUser2ExternalUser.convert(user);
            }
        }
        return externalUser;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
