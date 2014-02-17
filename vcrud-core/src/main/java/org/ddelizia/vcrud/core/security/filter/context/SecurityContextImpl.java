package org.ddelizia.vcrud.core.security.filter.context;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.security.exception.InvalidAuthorizationHeaderException;

import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

/**
 * @author ddelizia
 * @since 16/02/14 18:50
 */
public class SecurityContextImpl implements SecurityContext {

    private static final Logger LOGGER = Logger.getLogger(SecurityContextImpl.class);

    private final ExternalUser user;

    public SecurityContextImpl(ExternalUser user) {
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return user;
    }

    @Override
    public boolean isUserInRole(String role) {
        if(role == null){//equalsIgnoreCase(Role.anonymous.name())) {
            return true;
        }
        if(user == null) {
            throw new InvalidAuthorizationHeaderException();
        }
        return user.getRoles().contains(role);
    }

    public boolean isSecure() {
        return false;
    }

    public String getAuthenticationScheme() {
        return SecurityContext.BASIC_AUTH;
    }


}
