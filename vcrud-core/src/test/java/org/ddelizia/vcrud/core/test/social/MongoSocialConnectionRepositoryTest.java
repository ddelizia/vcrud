package org.ddelizia.vcrud.core.test.social;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.service.SessionTokenService;
import org.ddelizia.vcrud.core.security.service.converter.ConverterUser2ExternalUser;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.MongoSocialConnectionRepository;
import org.ddelizia.vcrud.core.social.repository.MongoSocialUsersConnectionRepository;
import org.ddelizia.vcrud.core.social.repository.SocialUserConnectionRepository;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.ddelizia.vcrud.core.usermanagement.service.impl.UserServiceImpl;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;
import org.springframework.social.facebook.api.Facebook;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ddelizia
 * @since 19/03/14 21:43
 */
public class MongoSocialConnectionRepositoryTest {

	private static final Logger LOGGER = Logger.getLogger(MongoSocialConnectionRepositoryTest.class);

	private MongoSocialUsersConnectionRepository mongoSocialUsersConnectionRepository;

	private Validator validator;

	private static final String PROVIDER_ID = "facebook";
	private static final String PROVIDER_USER_ID = "123456";
	private static final String ACCESS_TOKEN = "1234567890";

	private static final String USER_ID = "userId";


	private ConnectionFactoryLocator connectionFactoryLocator;
	private Connection<Facebook> connection;
	private ConnectionFactory connectionFactory;

	private TextEncryptor textEncryptor = Encryptors.noOpText();

	private SocialUserConnectionRepository socialUserConnectionRepository;
	private UserRepository userRepository;

	private SocialUserConnection socialUserConnection;
	private User user;

	private Set<String> providerIds = new HashSet<>();
	private List<SocialUserConnection> socialUsers;

	private UserService userService;
	private UserGroupService userGroupService;

	public AppConfig appConfig;
	public static final String APP_CONFIG_GETPROPERTY = "Property Return";

	@Before
	public void setUpRepository() {
		mockUserRepository();
		mockSocialUsers();
		mockServices();
		mockConnectionData();

		mongoSocialUsersConnectionRepository = new MongoSocialUsersConnectionRepository(
				connectionFactoryLocator, textEncryptor);
		mongoSocialUsersConnectionRepository.setSocialUserConnectionRepository(socialUserConnectionRepository);
		mongoSocialUsersConnectionRepository.setUserRepository(userRepository);
		mongoSocialUsersConnectionRepository.setUserService(userService);
		mongoSocialUsersConnectionRepository.setUserGroupService(userGroupService);
		Mockito.when(userRepository.save(Matchers.any(User.class))).thenAnswer(new Answer<Object>() {
			public User answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				user.setId(((User) args[0]).getId().toString());
				return (User) args[0];
			}
		});
		validator = Validation.buildDefaultValidatorFactory().getValidator();
	}


	private void mockConnectionData() {
		providerIds.add(PROVIDER_ID);
		connectionFactoryLocator = Mockito.mock(ConnectionFactoryLocator.class);
		connection = Mockito.mock(Connection.class);
		connectionFactory = Mockito.mock(ConnectionFactory.class);
		Mockito.when(connection.getKey()).thenReturn(new ConnectionKey(PROVIDER_ID, PROVIDER_USER_ID));
		Mockito.when(connectionFactoryLocator.registeredProviderIds()).thenReturn(providerIds);
		ConnectionData data = new ConnectionData(PROVIDER_ID, PROVIDER_USER_ID, "Test User", null, null, ACCESS_TOKEN, null, null, null);
		Mockito.when(connection.createData()).thenReturn(data);
		Mockito.when(connectionFactoryLocator.getConnectionFactory(Matchers.any(String.class))).thenReturn(connectionFactory);
		Mockito.when(connectionFactory.createConnection(Matchers.any(ConnectionData.class))).thenReturn(connection);
	}

	private void mockSocialUsers() {
		socialUserConnection = new SocialUserConnection();
		socialUserConnection.setUser(user);
		socialUserConnection.setAccessToken(ACCESS_TOKEN);
		socialUserConnection.setProviderId(PROVIDER_ID);
		socialUserConnection.setProviderUserId(PROVIDER_USER_ID);
		socialUserConnection.setDisplayName("Test User");
		socialUserConnection.setRank(1);
		socialUsers = new ArrayList<>();
		socialUsers.add(socialUserConnection);
		socialUserConnectionRepository = Mockito.mock(SocialUserConnectionRepository.class);
	}
	private void mockUserRepository() {
		user = new Customer();
		user.setId(USER_ID);
		userRepository = Mockito.mock(UserRepository.class);
		Mockito.when(userRepository.findOne(Matchers.any(String.class))).thenReturn(user);
	}

	private void mockServices(){
		userService = Mockito.mock(UserService.class);
		userGroupService = Mockito.mock(UserGroupService.class);
	}

	public void mockAppConfig(){
		appConfig = Mockito.mock(AppConfig.class);
		Mockito.when(appConfig.getProperty(
				Matchers.any(String.class),
				Matchers.eq(String.class),
				Matchers.any(String.class)
		)).thenReturn(APP_CONFIG_GETPROPERTY);
	}

	@Test
	public void firstTimeConnected() {
		Mockito.when(userGroupService.getAuthenticatedGroup()).thenReturn(new UserGroup());
		Mockito.when(userService.createCustomerInGroup(Matchers.any(UserGroup.class))).thenReturn((Customer)user);
		List<String> userIds = mongoSocialUsersConnectionRepository.findUserIdsWithConnection(connection);
		Assert.assertEquals(userIds.size(),1);
		Assert.assertEquals(userIds.iterator().next(),user.getId());
		Mockito.verify(socialUserConnectionRepository).save(Matchers.any(SocialUserConnection.class));
	}


	@Test
	public void alreadyRegistered() {
		Mockito.when(socialUserConnectionRepository.findByProviderIdAndProviderUserId(PROVIDER_ID, PROVIDER_USER_ID)).thenReturn(socialUsers);
		List<String> userIds = mongoSocialUsersConnectionRepository.findUserIdsWithConnection(connection);
		Assert.assertEquals(userIds.size(),1);
		Assert.assertEquals(userIds.iterator().next(),user.getId());
		Mockito.verify(userRepository, Mockito.never()).save(Matchers.any(User.class));
		Mockito.verify(socialUserConnectionRepository, Mockito.never()).save(Matchers.any(SocialUserConnection.class));
	}

	@Test
	public void validSocialLogin() {
		SessionToken sessionToken = new SessionToken();
		sessionToken.setId("token");

		mockAppConfig();

		UserGroup userGroupMocked = new UserGroup();

		UserGroupRepository userGroupRepositoryMocked = Mockito.mock(UserGroupRepository.class);
		Mockito.when(userGroupRepositoryMocked.findByGroupName(APP_CONFIG_GETPROPERTY)).thenReturn(userGroupMocked);

		SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
		Mockito.when(sessionTokenService.createSessionToken(user)).thenReturn(sessionToken);

		//((UserServiceImpl)userService).setAppConfig(appConfig);
		UserProfileBuilder builder = new UserProfileBuilder();
		UserProfile profile = builder.setFirstName("Tom").setLastName("Tucker").setEmail("tt@example.com").setUsername("ttucker").build();
		Mockito.when(connection.fetchUserProfile()).thenReturn(profile);

		Mockito.when(userGroupService.getAuthenticatedGroup()).thenReturn(userGroupMocked);
		Mockito.when(userService.createCustomerInGroup(Matchers.any(UserGroup.class))).thenReturn((Customer)user);
		Mockito.when(userService.createCustomerInGroup(Matchers.any(UserGroup.class))).thenReturn((Customer)user);
		Mockito.when(userService.isVerified(user)).thenReturn(true);

		Mockito.when(socialUserConnectionRepository.findByUser(user)).thenReturn(socialUsers);
		ConverterUser2ExternalUser testConverterUser2ExternalUser = new ConverterUser2ExternalUser();


		UserService testUserService = new UserServiceImpl();
		((UserServiceImpl)testUserService).setMongoSocialUsersConnectionRepository(mongoSocialUsersConnectionRepository);
		((UserServiceImpl)testUserService).setUserRepository(userRepository);
		((UserServiceImpl)testUserService).setAppConfig(appConfig);
		((UserServiceImpl)testUserService).setUserGroupRepository(userGroupRepositoryMocked);
		((UserServiceImpl)testUserService).setUserRepository(userRepository);
		((UserServiceImpl)testUserService).setSessionTokenService(sessionTokenService);
		((UserServiceImpl)testUserService).setConverterUser2ExternalUser(testConverterUser2ExternalUser);
		AuthenticatedUserToken token = testUserService.socialLogin(connection);

		testConverterUser2ExternalUser.setUserService(userService);
		testConverterUser2ExternalUser.setSocialUserConnectionRepository(socialUserConnectionRepository);

		ExternalUser user = testUserService.getUser(new ExternalUser(token.getUserId()), token.getUserId());
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getEmailAddress(), "tt@example.com");
		Assert.assertEquals(user.getFirstName(), "Tom");
	}

	@Test
	public void updateFromSocialLogin() {
		SessionToken sessionToken = new SessionToken();
		sessionToken.setId("token");

		mockAppConfig();

		UserGroup userGroupMocked = new UserGroup();

		UserGroupRepository userGroupRepositoryMocked = Mockito.mock(UserGroupRepository.class);
		Mockito.when(userGroupRepositoryMocked.findByGroupName(APP_CONFIG_GETPROPERTY)).thenReturn(userGroupMocked);

		SessionTokenService sessionTokenService = Mockito.mock(SessionTokenService.class);
		Mockito.when(sessionTokenService.createSessionToken(user)).thenReturn(sessionToken);

		//((UserServiceImpl)userService).setAppConfig(appConfig);
		UserProfileBuilder builder = new UserProfileBuilder();
		UserProfile profile = builder.setFirstName("Tom").setLastName("Tucker").setEmail("tt@example.com").setUsername("ttucker").build();
		Mockito.when(connection.fetchUserProfile()).thenReturn(profile);

		Mockito.when(userGroupService.getAuthenticatedGroup()).thenReturn(userGroupMocked);
		Mockito.when(userService.createCustomerInGroup(Matchers.any(UserGroup.class))).thenReturn((Customer)user);
		Mockito.when(userService.createCustomerInGroup(Matchers.any(UserGroup.class))).thenReturn((Customer)user);
		Mockito.when(userService.isVerified(user)).thenReturn(true);

		Mockito.when(socialUserConnectionRepository.findByUser(user)).thenReturn(socialUsers);
		ConverterUser2ExternalUser testConverterUser2ExternalUser = new ConverterUser2ExternalUser();


		UserService testUserService = new UserServiceImpl();
		((UserServiceImpl)testUserService).setMongoSocialUsersConnectionRepository(mongoSocialUsersConnectionRepository);
		((UserServiceImpl)testUserService).setUserRepository(userRepository);
		((UserServiceImpl)testUserService).setAppConfig(appConfig);
		((UserServiceImpl)testUserService).setUserGroupRepository(userGroupRepositoryMocked);
		((UserServiceImpl)testUserService).setUserRepository(userRepository);
		((UserServiceImpl)testUserService).setSessionTokenService(sessionTokenService);
		((UserServiceImpl)testUserService).setConverterUser2ExternalUser(testConverterUser2ExternalUser);
		AuthenticatedUserToken token = testUserService.socialLogin(connection);

		testConverterUser2ExternalUser.setUserService(userService);
		testConverterUser2ExternalUser.setSocialUserConnectionRepository(socialUserConnectionRepository);

		//login again and update
		profile = builder.setFirstName("Foo").setLastName("Bar").setEmail("foobar@example.com").setUsername("foobar").build();
		Mockito.when(connection.fetchUserProfile()).thenReturn(profile);
		AuthenticatedUserToken token2 = testUserService.socialLogin(connection);

		ExternalUser user = testUserService.getUser(new ExternalUser(token2.getUserId()), token2.getUserId());
		//Assert.assertNotNull(user);
		Assert.assertEquals(user.getEmailAddress(), "foobar@example.com");
		Assert.assertEquals(user.getFirstName(), "Foo");
	}

}
