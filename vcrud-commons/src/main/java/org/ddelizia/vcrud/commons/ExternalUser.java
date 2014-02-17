package org.ddelizia.vcrud.commons;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */

public class ExternalUser implements Principal {

    private String id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private boolean isVerified;

    private Set<String> roles;

    private List<SocialProfile> socialProfiles = new ArrayList<>();

    public ExternalUser() {}

    public ExternalUser(String userId) {
        this.id = userId;
    }

    /*
    public ExternalUser(User user, SessionToken activeSession) {
        this(user);
    }     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<SocialProfile> getSocialProfiles() {
        return socialProfiles;
    }

    public String getId() {
        return id;
    }

    public boolean isVerified(boolean verified) {
        return isVerified;
    }

    public String getName() {
        return emailAddress;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public void setSocialProfiles(List<SocialProfile> socialProfiles) {
        this.socialProfiles = socialProfiles;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

}
