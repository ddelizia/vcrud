package org.ddelizia.vcrud.social.model;

import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.usermanagement.User;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/11/13
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class SocialUser extends VcrudModel{

    @Column(name = "accessToken", nullable = false)
    private String accessToken;

    @Column(name = "providerId")
    private String providerId;

    @Column(name = "providerUserId")
    private String providerUserId;

    @Column(name = "rank", nullable = false)
    private Integer rank;

    @Column(name = "displayName")
    private String displayName;

    @Column(name = "profileUrl")
    private String profileUrl;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "secret")
    private String secret;

    @Column(name = "refreshToken")
    private String refreshToken;

    @Column(name = "expireTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expireTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_ref")
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

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }
}
