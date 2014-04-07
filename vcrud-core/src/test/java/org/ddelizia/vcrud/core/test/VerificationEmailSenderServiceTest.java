package org.ddelizia.vcrud.core.test;

import org.apache.commons.lang.RandomStringUtils;
import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.commons.PasswordRequest;
import org.ddelizia.vcrud.commons.client.CreateUserRequest;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.mail.mock.MockJavaMailSender;
import org.ddelizia.vcrud.core.security.model.SessionToken;
import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.security.service.VerificationEmailSenderService;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.test.util.MatcherAssertionErrors;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/02/14
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class VerificationEmailSenderServiceTest extends VcrudCoreIntegrationTest {

	@Resource
	private MockJavaMailSender mailSender;

    @Resource(name = VerificationEmailSenderService.DEFAULT_BEAN_NAME)
    private VerificationEmailSenderService verificationEmailSenderService;

    @Resource(name = UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

	@Resource
	private UserRepository userRepository;

	@Resource
	private AppConfig appConfig;

	private final static String VERIFICATION_USER_NAME = "verificationUser";
	private final static String VERIFICATION_USER_EMAIL = "verificationUser@email.com";

	@Test
	public void sendVerificationEmail() throws Exception {
		AuthenticatedUserToken userToken = createUserWithRandomUserName(
				getUserManagmentDataFactory().getUserGroupAuthenticated()
		);
		User user = userRepository.findOne(userToken.getUserId());
		VerificationToken token = new VerificationToken(
				user,
				VerificationToken.VerificationTokenType.emailVerification,
				120);

		verificationEmailSenderService.sendVerificationEmail(new EmailServiceTokenData(
				user,
				token,
				appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
				));

		assertOnMailResult(user, token);

    }

	@Test
    public void sendRegistrationEmail() throws Exception {
	    AuthenticatedUserToken userToken = createUserWithRandomUserName(
			    getUserManagmentDataFactory().getUserGroupAuthenticated()
	    );
	    User user = userRepository.findOne(userToken.getUserId());
	    VerificationToken token = new VerificationToken(
			    user,
			    VerificationToken.VerificationTokenType.emailRegistration,
			    120);

	    verificationEmailSenderService.sendRegistrationEmail(new EmailServiceTokenData(
			    user,
			    token,
			    appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
	    ));

	    assertOnMailResult(user, token);
    }

	@Test
    public void sendLostPasswordEmail() throws Exception {
	    AuthenticatedUserToken userToken = createUserWithRandomUserName(
			    getUserManagmentDataFactory().getUserGroupAuthenticated()
	    );
	    User user = userRepository.findOne(userToken.getUserId());
	    VerificationToken token = new VerificationToken(
			    user,
			    VerificationToken.VerificationTokenType.lostPassword,
			    120);

	    verificationEmailSenderService.sendLostPasswordEmail(new EmailServiceTokenData(
			    user,
			    token,
			    appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)
	    ));

	    assertOnMailResult(user, token);
    }

    @Override
    public void vcrudAfter() {
        getUserManagmentDataFactory().removeData();
	    getMongoHelper().removeAllDataFromCollection(SessionToken.class);
    }

    @Override
    public void vcrudBefore() {
	    getUserManagmentDataFactory().createData();
	    mailSender.resetMessages();
    }

    private AuthenticatedUserToken createUserWithRandomUserName(UserGroup userGroup) {
	    CreateUserRequest request = getDefaultCreateUserRequest();
	    return userService.createUser(request, userGroup);
    }

    private CreateUserRequest getDefaultCreateUserRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUser(getUser());
        request.setPassword(new PasswordRequest("password"));
        return request;
    }

    private ExternalUser getUser() {
        ExternalUser user = new ExternalUser();
	    user.setFirstName("John");
	    user.setLastName("Smith");
	    user.setEmailAddress(createRandomEmailAddress());
        return user;
    }

    private String createRandomEmailAddress() {
        return RandomStringUtils.randomAlphabetic(8) + "@example.com";
    }

	private void assertOnMailResult(User user, VerificationToken token) throws MessagingException, IOException {
		List<MimeMessage> messages = mailSender.getMessages();
		MatcherAssertionErrors.assertThat(messages.size(), Is.is(1));
		MimeMessage message = messages.get(0);
		MatcherAssertionErrors.assertThat(message.getAllRecipients()[0].toString(), CoreMatchers.is((user.getEmail())));
		Multipart multipart = (Multipart)message.getContent();
		String content = (String)multipart.getBodyPart(0).getContent();
		Assert.assertTrue(content.contains(new String(Base64.encode(token.getToken().getBytes()))));
	}
}
