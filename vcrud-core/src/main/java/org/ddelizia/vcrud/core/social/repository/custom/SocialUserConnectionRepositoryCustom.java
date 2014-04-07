package org.ddelizia.vcrud.core.social.repository.custom;

import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserConnectionRepositoryCustom {

    public SocialUserConnection findByUserNameAndProviderIdMaxRank(String providerId, String providerUserId);

    public List<SocialUserConnection> findByUserIdInProviderUsers(String userId, MultiValueMap<String, String> providerUsers);

    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds);

	public List<SocialUserConnection> findByUserId(String userName);

	public List<SocialUserConnection> findByUserIdAndProviderId(String username, String providerId);

	public SocialUserConnection findByUserIdAndProviderIdAndProviderUserId(String username, String providerId, String providerUserId);

}
