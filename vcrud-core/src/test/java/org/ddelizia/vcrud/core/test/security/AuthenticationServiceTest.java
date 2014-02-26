package org.ddelizia.vcrud.core.test.security;

import org.ddelizia.vcrud.commons.PasswordRequest;
import org.ddelizia.vcrud.commons.client.LostPasswordRequest;
import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.security.repository.VerificationTokenRepository;
import org.ddelizia.vcrud.core.security.service.AuthenticationService;
import org.ddelizia.vcrud.core.security.service.CryptoService;
import org.ddelizia.vcrud.core.security.service.EmailAuthenticationServiceGateway;
import org.ddelizia.vcrud.core.security.service.VerificationTokenService;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.ddelizia.vcrud.core.security.service.impl.AuthenticationServiceImpl;
import org.ddelizia.vcrud.core.security.service.impl.VerificationTokenServiceImpl;
import org.ddelizia.vcrud.core.usermanagement.model.Customer;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.repository.UserRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.hamcrest.core.Is;
import org.hamcrest.core.IsNot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.codec.Base64;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 25/02/14
 * Time: 21:27
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationServiceTest {


    private UserRepository userRepository;
    private VerificationTokenRepository verificationTokenRepository;
    private EmailAuthenticationServiceGateway emailAuthenticationServiceGateway;
    private AppConfig appConfig;
    private UserService userService;
    private VerificationTokenService verificationTokenService;
    private CryptoService cryptoService;

    private AuthenticationService authenticationService;

    private Validator validator;
    private List<String> tokens;

    @Before
    public void setUp() {
        tokens = new ArrayList<>();

        emailAuthenticationServiceGateway = new EmailAuthenticationServiceGateway() {
            public void sendVerificationToken(EmailServiceTokenData model) {
                tokens.add(model.getToken());
            }
        };

        validator = Validation.buildDefaultValidatorFactory().getValidator();

        userRepository = Mockito.mock(UserRepository.class);
        verificationTokenRepository = Mockito.mock(VerificationTokenRepository.class);
        appConfig = Mockito.mock(AppConfig.class);
        userService = Mockito.mock(UserService.class);
        verificationTokenService = Mockito.mock(VerificationTokenService.class);
        cryptoService = Mockito.mock(CryptoService.class);

        authenticationService = new AuthenticationServiceImpl();
        ((AuthenticationServiceImpl)authenticationService).setUserRepository(userRepository);
        ((AuthenticationServiceImpl)authenticationService).setVerificationTokenRepository(verificationTokenRepository);
        ((AuthenticationServiceImpl)authenticationService).setEmailAuthenticationServiceGateway(emailAuthenticationServiceGateway);
        ((AuthenticationServiceImpl)authenticationService).setAppConfig(appConfig);
        ((AuthenticationServiceImpl)authenticationService).setUserService(userService);
        ((AuthenticationServiceImpl)authenticationService).setVerificationTokenService(verificationTokenService);
        ((AuthenticationServiceImpl)authenticationService).setCryptoService(cryptoService);
        ((AuthenticationServiceImpl)authenticationService).setValidator(validator);


        Mockito.when(appConfig.getProperty(AppConfig.TOKEN_EMAIL_VERIFICATION_DURATION, Integer.class, null)).thenReturn(1);
        Mockito.when(appConfig.getProperty(AppConfig.TOKEN_EMAIL_REGISTRATION_DURATION, Integer.class, null)).thenReturn(1);
        Mockito.when(appConfig.getProperty(AppConfig.TOKEN_LOST_PASSWORD_DURATION, Integer.class, null)).thenReturn(1);
        Mockito.when(appConfig.getProperty(AppConfig.HOSTNAME_PROPERTY, String.class, null)).thenReturn("http://localhost:8080");
    }


    @Test
    public void sendLostPasswordToken() {
        User user = generateTestCustomer();
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        VerificationToken verificationToken = authenticationService.sendLostPasswordToken(new LostPasswordRequest(user.getEmail()));
        Assert.assertEquals(verificationToken.getUser(), user);
        Assert.assertEquals(tokens.size(), 1);

        String sentToken = tokens.get(0);
        Assert.assertNotNull(sentToken);
        Assert.assertEquals(sentToken, verificationToken.getToken());
        Assert.assertEquals(verificationToken.getTokenType(), VerificationToken.VerificationTokenType.lostPassword);
    }

    @Test
    public void sendEmailToken() {
        User user = generateTestCustomer();
        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        VerificationToken verificationToken = authenticationService.sendEmailVerificationToken(user.getId());
        Assert.assertEquals(verificationToken.getUser(), user);
        Assert.assertEquals(tokens.size(), 1);

        String sentToken = tokens.get(0);
        Assert.assertNotNull(sentToken);
        Assert.assertEquals(sentToken, verificationToken.getToken());
        Assert.assertEquals(verificationToken.getTokenType(), VerificationToken.VerificationTokenType.emailVerification);
    }

    @Test
    public void sendRegistrationToken() {
        User user = generateTestCustomer();
        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        VerificationToken verificationToken = authenticationService.sendEmailRegistrationToken(user.getId());
        Assert.assertEquals(verificationToken.getUser(), user);
        Assert.assertEquals(tokens.size(), 1);

        String sentToken = tokens.get(0);
        Assert.assertNotNull(sentToken);
        Assert.assertEquals(sentToken, verificationToken.getToken());
        Assert.assertEquals(verificationToken.getTokenType(), VerificationToken.VerificationTokenType.emailRegistration);
    }

    @Test
    public void verify() {
        User user = generateTestCustomer();
        Mockito.when(userRepository.findOne(user.getId())).thenReturn(user);

        VerificationToken verificationToken = authenticationService.sendEmailRegistrationToken(user.getId());
        Mockito.when(verificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(verificationToken);

        String encodedToken = new String(Base64.encode(verificationToken.getToken().getBytes()));
        VerificationToken verifiedToken = authenticationService.verify(encodedToken);

        Assert.assertEquals(verifiedToken.isVerified(), true);
    }

    @Test
    public void generateEmailVerificationToken() {
        User user = generateTestCustomer();
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        Mockito.when(userRepository.save(user)).thenReturn(user);
        VerificationToken verificationToken = authenticationService.generateEmailVerificationToken(user.getEmail());
        Assert.assertEquals(verificationToken.getUser(), user);
        Assert.assertEquals(tokens.size(), 1);


        String sentToken = tokens.get(0);
        Assert.assertNotNull(sentToken);
        Assert.assertEquals(sentToken, verificationToken.getToken());

        UUID.fromString(sentToken);
    }

    @Test
    public void resetPassword() throws Exception {
        User user = generateTestCustomer();
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(user);
        VerificationToken verificationToken = authenticationService.sendLostPasswordToken(new LostPasswordRequest(user.getEmail()));

        PasswordRequest passwordRequest = new PasswordRequest("newpassword");
        String encodedPassword = "encodedPassword";
        Mockito.when(verificationTokenRepository.findByToken(verificationToken.getToken())).thenReturn(verificationToken);
        Mockito.when(cryptoService.hashPassword(passwordRequest.getPassword(), user)).thenReturn(encodedPassword);
        String encodedToken = new String(Base64.encode(verificationToken.getToken().getBytes()));
        VerificationToken verifiedToken = authenticationService.resetPassword(encodedToken, passwordRequest);

        Assert.assertEquals(verifiedToken.isVerified(), true);
        Assert.assertEquals(user.getPassword(),encodedPassword);
    }

    private User generateTestCustomer() {
        User user = new Customer();
        user.setId("id");
        user.setEmail("test@example.com");
        user.setName("name");
        return user;
    }
}
