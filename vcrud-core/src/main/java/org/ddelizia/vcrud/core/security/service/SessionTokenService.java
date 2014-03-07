package org.ddelizia.vcrud.core.security.service;

import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.usermanagement.model.User;

/**
 * @author ddelizia
 * @since 28/02/14 13:50
 */
public interface SessionTokenService {

	public static final String DEFAULT_BEAN_NAME = "defaultSessionTokenService";

	/**
	 * Create session token from given user
	 * @param user
	 * @return
	 */
	public SessionToken createSessionToken (User user);

}
