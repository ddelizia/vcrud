package org.ddelizia.vcrud.core.security.model;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.usermanagement.VcrudPrincipal;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 17:51
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "VerificationToken")
public class VerificationToken extends VcrudItem{

    private static final int DEFAULT_EXPIRY_TIME_IN_MINS = 60 * 24; //24 hours

    private String token;
    private DateTime expiryDate;
    private VerificationTokenType tokenType;
    private boolean verified;
    private VcrudPrincipal principal;


    public VerificationToken() {
        super();
        this.token = UUID.randomUUID().toString();
        this.expiryDate = calculateExpiryDate(DEFAULT_EXPIRY_TIME_IN_MINS);
    }

    private DateTime calculateExpiryDate(int expiryTimeInMinutes) {
        DateTime now = new DateTime();
        return now.plusMinutes(expiryTimeInMinutes);
    }

    public enum VerificationTokenType {
        lostPassword, emailVerification, emailRegistration
    }

    public boolean hasExpired() {
        DateTime tokenDate = new DateTime(getExpiryDate());
        return tokenDate.isBeforeNow();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(DateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public VerificationTokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(VerificationTokenType tokenType) {
        this.tokenType = tokenType;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public VcrudPrincipal getPrincipal() {
        return principal;
    }

    public void setPrincipal(VcrudPrincipal principal) {
        this.principal = principal;
    }
}
