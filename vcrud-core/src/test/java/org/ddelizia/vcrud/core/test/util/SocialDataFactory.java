package org.ddelizia.vcrud.core.test.util;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.SocialUserConnectionRepository;

import javax.annotation.Resource;

/**
 * @author ddelizia
 * @since 10/03/14 09:12
 */
public class SocialDataFactory extends DataFactory{

	private static final Logger LOGGER = Logger.getLogger(SocialDataFactory.class);

	@Resource
	private UserManagmentDataFactory userManagmentDataFactory;

	@Resource
	private SocialUserConnectionRepository socialUserConnectionRepository;


	public static final String SOCIAL_USER_VER_REST_PRO_USER_ID_1 = "987654";
	public static final int SOCIAL_USER_VER_REST_RANK_1 = 1;
	public static final String SOCIAL_USER_VER_REST_TOKEN_1 = "1234567890";


	public static final String SOCIAL_USER_VER_REST_PRO_USER_ID_2 = "123456";
	public static final int SOCIAL_USER_VER_REST_RANK_2 = 2;
	public static final String SOCIAL_USER_VER_REST_TOKEN_2 = "1234567890";

	public static final String SOCIAL_USER_VER_REST_PRO_USER_ID_3 = "918273";
	public static final int SOCIAL_USER_VER_REST_RANK_3 = 1;
	public static final String SOCIAL_USER_VER_REST_TOKEN_3 = "1234567890";

	public static final String SOCIAL_USER_PRO_ID_FACEBOOK = "facebook";
	public static final String SOCIAL_USER_PRO_ID_TWITTER = "twitter";

	@Override
	public void createData() {
		userManagmentDataFactory.createData();

		SocialUserConnection socialUserVerRest = new SocialUserConnection();
		socialUserVerRest.setUser(userManagmentDataFactory.getCustomerVerifiedRestGroup());
		socialUserVerRest.setAccessToken(SOCIAL_USER_VER_REST_TOKEN_1);
		socialUserVerRest.setProviderUserId(SOCIAL_USER_VER_REST_PRO_USER_ID_1);
		socialUserVerRest.setProviderId(SOCIAL_USER_PRO_ID_FACEBOOK);
		socialUserVerRest.setRank(SOCIAL_USER_VER_REST_RANK_1);
		socialUserConnectionRepository.save(socialUserVerRest);

		SocialUserConnection socialUserVerRest2 = new SocialUserConnection();
		socialUserVerRest2.setUser(userManagmentDataFactory.getCustomerVerifiedRestGroup());
		socialUserVerRest2.setAccessToken(SOCIAL_USER_VER_REST_TOKEN_2);
		socialUserVerRest2.setProviderUserId(SOCIAL_USER_VER_REST_PRO_USER_ID_2);
		socialUserVerRest2.setProviderId(SOCIAL_USER_PRO_ID_FACEBOOK);
		socialUserVerRest2.setRank(SOCIAL_USER_VER_REST_RANK_2);
		socialUserConnectionRepository.save(socialUserVerRest2);

		SocialUserConnection socialUserVerRest3 = new SocialUserConnection();
		socialUserVerRest3.setUser(userManagmentDataFactory.getCustomerVerifiedRestGroup());
		socialUserVerRest3.setAccessToken(SOCIAL_USER_VER_REST_TOKEN_3);
		socialUserVerRest3.setProviderUserId(SOCIAL_USER_VER_REST_PRO_USER_ID_3);
		socialUserVerRest3.setProviderId(SOCIAL_USER_PRO_ID_TWITTER);
		socialUserVerRest3.setRank(SOCIAL_USER_VER_REST_RANK_3);
		socialUserConnectionRepository.save(socialUserVerRest3);

	}

	@Override
	public void removeData() {
		userManagmentDataFactory.removeData();
		getMongoHelper().removeAllDataFromCollection(SocialUserConnection.class);
	}

	public UserManagmentDataFactory getUserManagmentDataFactory() {
		return userManagmentDataFactory;
	}
}
