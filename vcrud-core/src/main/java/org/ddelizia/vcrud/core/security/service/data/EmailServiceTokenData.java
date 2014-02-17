package org.ddelizia.vcrud.core.security.service.data;

import org.ddelizia.vcrud.core.security.model.VerificationToken;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.springframework.security.crypto.codec.Base64;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 08/02/14
 * Time: 11:20
 * To change this template use File | Settings | File Templates.
 */
public class EmailServiceTokenData implements Serializable {

    private final String emailAddress;
    private final String token;
    private final VerificationToken.VerificationTokenType tokenType;
    private final String hostNameUrl;


    public EmailServiceTokenData(User user, VerificationToken token, String hostNameUrl)  {
        this.emailAddress = user.getEmail();
        this.token = token.getToken();
        this.tokenType = token.getTokenType();
        this.hostNameUrl = hostNameUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getEncodedToken() {
        return new String(Base64.encode(token.getBytes()));
    }

    public String getToken() {
        return token;
    }

    public VerificationToken.VerificationTokenType getTokenType() {
        return tokenType;
    }

    public String getHostNameUrl() {
        return hostNameUrl;
    }
}
