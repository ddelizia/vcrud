package org.ddelizia.vcrud.core.social.repository.custom;

import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 03/02/14
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserConnectionRepositoryCustom {

    public SocialUserConnection findByUserNameAndProviderIdMaxRank(String providerId, String providerUserId);

    public List<SocialUserConnection> findByUserNameInProviderUsers(String userId, MultiValueMap<String, String> providerUsers);

}
