package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.security.filter.AuthorizationRequestContext;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public interface AuthorizationService {

    public static final String DEFAULT_BEAN_NAME = "defaultAuthorizationService";

    /**
     * Given an AuthorizationRequestContext validate and authorize a User
     *
     * @param authorizationRequestContext the context required to authorize a user for a particular request
     * @return ExternalUser
     */
    public ExternalUser authorize(AuthorizationRequestContext authorizationRequestContext);

}
