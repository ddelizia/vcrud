package org.ddelizia.vcrud.core.security.service.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.repository.SessionTokenRepository;
import org.ddelizia.vcrud.core.security.service.SessionTokenService;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ddelizia
 * @since 28/02/14 13:53
 */
@Service(SessionTokenService.DEFAULT_BEAN_NAME)
public class SessionTokenServiceImpl implements SessionTokenService{

	private static final Logger LOGGER = Logger.getLogger(SessionTokenServiceImpl.class);

	@Autowired
	private SessionTokenRepository sessionTokenRepository;


	/**
	 * Create session token from given user
	 *
	 * @param user
	 * @return
	 */
	@Override
	public SessionToken createSessionToken(User user) {
		SessionToken sessionToken = new SessionToken(user);
		sessionTokenRepository.save(sessionToken);
		return sessionToken;
	}
}
