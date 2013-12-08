package org.ddelizia.vcrud.social.dao;

import org.ddelizia.vcrud.core.dao.Dao;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.social.model.SocialUser;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:58
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserDao extends Dao<SocialUser> {

    public List<SocialUser> findIn(User user, MultiValueMap<String, String> providerUsers);

    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds);

    public List<User> findUsersModelConnectedTo(String providerId, Set<String> providerUserIds);

}
