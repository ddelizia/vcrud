package org.ddelizia.vcrud.core.test;

import org.apache.commons.lang.RandomStringUtils;
import org.ddelizia.vcrud.commons.AuthenticatedUserToken;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.commons.PasswordRequest;
import org.ddelizia.vcrud.commons.client.CreateUserRequest;
import org.ddelizia.vcrud.core.security.service.AuthenticationEmailSenderService;
import org.ddelizia.vcrud.core.security.service.data.EmailServiceTokenData;
import org.ddelizia.vcrud.core.test.util.UserManagmentDataFactory;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/02/14
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationEmailSenderServiceTest extends AbstractJunit4Vcrud {

    @Autowired
    private UserManagmentDataFactory userManagmentDataFactory;

    @Autowired
    @Qualifier(AuthenticationEmailSenderService.DEFAULT_BEAN_NAME)
    private AuthenticationEmailSenderService authenticationEmailSenderService;

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    public void sendVerificationEmail(){

    }

    public void sendRegistrationEmail(){

    }

    public void sendLostPasswordEmail(){

    }

    @Override
    public void vcrudAfter() {
        userManagmentDataFactory.removeData();
    }

    @Override
    public void vcrudBefore() {
        userManagmentDataFactory.createData();
    }

    private AuthenticatedUserToken createUserWithRandomUserName(UserGroup userGroup) {

    }

    private CreateUserRequest getDefaultCreateUserRequest() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUser(getUser());
        request.setPassword(new PasswordRequest("password"));
        return request;
    }

    private ExternalUser getUser() {
        ExternalUser user = ExternalUserBuilder.create().withFirstName("John")
                .withLastName("Smith")
                .withEmailAddress(createRandomEmailAddress())
                .build();
        return user;
    }

    private String createRandomEmailAddress() {
        return RandomStringUtils.randomAlphabetic(8) + "@example.com";
    }
}
