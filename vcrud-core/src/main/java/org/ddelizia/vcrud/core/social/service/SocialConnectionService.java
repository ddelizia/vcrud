package org.ddelizia.vcrud.core.social.service;

import org.springframework.social.connect.Connection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 14:54
 * To change this template use File | Settings | File Templates.
 */
public interface SocialConnectionService {

    public AuthenticatedUserToken socialLogin(Connection<?> connection);
}
