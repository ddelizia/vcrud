package org.ddelizia.vcrud.core.security.filter;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import com.sun.jersey.spi.container.ResourceFilter;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.filter.context.SecurityContextImpl;
import org.ddelizia.vcrud.core.security.service.AuthorizationService;
import org.ddelizia.vcrud.core.security.service.impl.RequestSigningAuthorizationService;
import org.ddelizia.vcrud.core.security.service.impl.SessionTokenAuthorizationService;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
@Component
@Provider
public class SecurityContextFilter implements ResourceFilter, ContainerRequestFilter{

    protected static final String HEADER_AUTHORIZATION = "Authorization";

    protected static final String HEADER_DATE = "x-java-rest-date";

    protected static final String HEADER_NONCE = "nonce";

    @Autowired
    private AuthorizationService authorizationService;

    private AppConfig appConfig;

    @Autowired
    public SecurityContextFilter(UserRepository userRepository, UserService userService, AppConfig appConfig) {
        this.appConfig = appConfig;

    }

    /**
     * If there is an Authorisation header in the request extract the session token and retrieve the user
     *
     * Delegate to the AuthorizationService to validate the request
     *
     * If the request has a valid session token and the user is validated then a user object will be added to the security context
     *
     * Any Resource Controllers can assume the user has been validated and can merely authorize based on the role
     *
     * Resources with @PermitAll annotation do not require an Authorization header but will still be filtered
     *
     * @param request the ContainerRequest to filter
     *
     * @return the ContainerRequest with a SecurityContext added
     */
    public ContainerRequest filter(ContainerRequest request) {
        String authToken = request.getHeaderValue(HEADER_AUTHORIZATION);
        String requestDateString = request.getHeaderValue(HEADER_DATE);
        String nonce = request.getHeaderValue(HEADER_NONCE);
        AuthorizationRequestContext context = new AuthorizationRequestContext(request.getPath(), request.getMethod(),
                requestDateString, nonce, authToken);
        ExternalUser externalUser = authorizationService.authorize(context);
        request.setSecurityContext(new SecurityContextImpl(externalUser));
        return request;
    }



    public ContainerRequestFilter getRequestFilter() {
        return this;
    }

    public ContainerResponseFilter getResponseFilter() {
        return null;
    }

    public void setAppConfig(AppConfig appConfig) {
        this.appConfig = appConfig;
    }
}
