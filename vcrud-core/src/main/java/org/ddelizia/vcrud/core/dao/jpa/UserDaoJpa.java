package org.ddelizia.vcrud.core.dao.jpa;

import org.ddelizia.vcrud.core.dao.UserDao;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.springframework.stereotype.Component;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */

@Component("vcrudUserDao")
public class UserDaoJpa extends DaoJpa<User> implements UserDao{


}
