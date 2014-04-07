package org.ddelizia.vcrud.core.usermanagement.model;

import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:04
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "User")
public abstract class User extends VcrudPrincipal {

    private String email;

    private String password;

    private String firstName;

    private String middleName;

    private String lastName;

    @DBRef
    private Set<UserGroup> userGroups = new HashSet<>();

    private DateTime expireDate;

    private DateTime credentialsExpireTime;

    private Boolean accountLocked = true;

    private Boolean enabled = false;

    public Set<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Set<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public DateTime getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(DateTime expireDate) {
        this.expireDate = expireDate;
    }

    public DateTime getCredentialsExpireTime() {
        return credentialsExpireTime;
    }

    public void setCredentialsExpireTime(DateTime credentialsExpireTime) {
        this.credentialsExpireTime = credentialsExpireTime;
    }

    public Boolean getAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(Boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
