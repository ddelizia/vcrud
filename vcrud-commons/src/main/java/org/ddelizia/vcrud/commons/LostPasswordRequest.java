package org.ddelizia.vcrud.commons;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 18:27
 * To change this template use File | Settings | File Templates.
 */
public class LostPasswordRequest {

    private String emailAddress;

    public LostPasswordRequest() {}

    public LostPasswordRequest(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
