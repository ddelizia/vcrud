package org.ddelizia.vcrud.core.test.social;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.SocialUserConnectionRepository;
import org.ddelizia.vcrud.core.test.VcrudCoreIntegrationTest;
import org.ddelizia.vcrud.core.test.util.SocialDataFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ddelizia
 * @since 10/03/14 09:08
 */
public class SocialUserConnectionRepositoryTest extends VcrudCoreIntegrationTest {

	private static final Logger LOGGER = Logger.getLogger(SocialUserConnectionRepositoryTest.class);
	
	@Resource
	private SocialUserConnectionRepository socialUserConnectionRepository;

	@Resource
	private SocialDataFactory socialDataFactory;

	@Test
	public void findByUserId (){
		List<SocialUserConnection> socialUserConnectionList = socialUserConnectionRepository.findByUserId(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId());
		Assert.assertTrue(!socialUserConnectionList.isEmpty());
		Assert.assertTrue(socialUserConnectionList.size()==3);
	}

	@Test
	public void findByUserIdAndProviderId (){
		List<SocialUserConnection> socialUserConnectionList = socialUserConnectionRepository.findByUserIdAndProviderId(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId(),
				SocialDataFactory.SOCIAL_USER_PRO_ID_FACEBOOK);
		Assert.assertTrue(!socialUserConnectionList.isEmpty());
		Assert.assertTrue(socialUserConnectionList.size()==2);
	}

	@Test
	public void findByUserIdAndProviderIdAndProviderUserId (){
		SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId(),
				SocialDataFactory.SOCIAL_USER_PRO_ID_FACEBOOK,
				SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_1);
		Assert.assertNotNull(socialUserConnection);
		Assert.assertTrue(socialUserConnection.getAccessToken().equals(SocialDataFactory.SOCIAL_USER_VER_REST_TOKEN_1));
	}

	@Test
	public void findByUserNameAndProviderIdMaxRank(){
		SocialUserConnection socialUserConnection = socialUserConnectionRepository.findByUserNameAndProviderIdMaxRank(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId(),
				SocialDataFactory.SOCIAL_USER_PRO_ID_FACEBOOK);
		Assert.assertNotNull(socialUserConnection);
		Assert.assertTrue(socialUserConnection.getUser().getId().equals(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId()
		));
	}

	@Test
	public void findByUserIdInProviderUsers(){
		MultiValueMap<String, String> providerUsers = new LinkedMultiValueMap<>();
		providerUsers.put(SocialDataFactory.SOCIAL_USER_PRO_ID_FACEBOOK, Arrays.asList(
				SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_1,
				SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_2));
		providerUsers.put(SocialDataFactory.SOCIAL_USER_PRO_ID_TWITTER, Arrays.asList(
				SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_3));
		List<SocialUserConnection> socialUserConnectionList = socialUserConnectionRepository.findByUserIdInProviderUsers(
				socialDataFactory.getUserManagmentDataFactory().getCustomerVerifiedRestGroup().getId(),
				providerUsers
		);

		Assert.assertTrue(socialUserConnectionList.size()==3);
	}

	@Test
	public void findUsersConnectedTo(){
		Set<String> socialUsers = socialUserConnectionRepository.findUsersConnectedTo(
				SocialDataFactory.SOCIAL_USER_PRO_ID_FACEBOOK,
				new HashSet<String>(Arrays.asList(
						SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_1,
						SocialDataFactory.SOCIAL_USER_VER_REST_PRO_USER_ID_2
				))
		);

		Assert.assertTrue(socialUsers.size()==1);
	}

	/**
	 * Implement this method instead of @After
	 */
	@Override
	public void vcrudAfter() {
		socialDataFactory.removeData();
	}

	/**
	 * Implement this method instead of @Before
	 */
	@Override
	public void vcrudBefore() {
		socialDataFactory.createData();
	}
}
