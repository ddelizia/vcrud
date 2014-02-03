package org.ddelizia.vcrud.core.social.model;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.VcrudPrincipal;
import org.joda.time.DateTime;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 31/01/14
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Document(collection = "SocialUserConnection")
public class SocialUserConnection extends VcrudItem{

    private String accessToken;

    private String providerId;

    private String providerUserId;

    private Integer rank;

    private String displayName;

    private String profileUrl;

    private String imageUrl;

    private String secret;

    private String refreshToken;

    private DateTime expireTime;

    @DBRef
    private User user;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public DateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(DateTime expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
