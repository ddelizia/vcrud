package org.ddelizia.vcrud.core.dao;

import org.ddelizia.vcrud.model.social.SocialUser;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserDao extends Dao<SocialUser> {

    public List<SocialUser> findIn(String userId, MultiValueMap<String, String> providerUsers);

}
