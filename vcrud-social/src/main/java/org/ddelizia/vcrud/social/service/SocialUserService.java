package org.ddelizia.vcrud.social.service;

import org.ddelizia.vcrud.model.usermanagement.User;
import org.springframework.social.connect.UsersConnectionRepository;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:01
 * To change this template use File | Settings | File Templates.
 */
public interface SocialUserService extends UsersConnectionRepository{

    public User getUserFromSocialUser(String userName, String accessToken);

}
