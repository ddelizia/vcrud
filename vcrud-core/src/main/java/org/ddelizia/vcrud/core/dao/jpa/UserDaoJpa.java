package org.ddelizia.vcrud.core.dao.jpa;

import org.ddelizia.vcrud.core.dao.UserDao;
import org.ddelizia.vcrud.model.social.SocialUser_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.springframework.stereotype.Component;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */

@Component("org.ddelizia.vcrud.core.dao.jpa.UserDaoJpa")
public class UserDaoJpa extends DaoJpa<User> implements UserDao{


    @Override
    public List<User> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        String param1="param1";
        String param2="param2";

        String queryString = "SELECT u " +
                "FROM " + User.class.getName() + " u INNER JOIN s." + User_.socialUsers + " s " +
                "WHERE s."+SocialUser_.providerId+"= :"+param1+" "+
                "AND s."+SocialUser_.accessToken+"in :"+param2+" ";


        Query query = getEntityManager().createQuery(queryString)
                .setParameter(param1, providerId)
                .setParameter(param2, providerUserIds);;

        List<User> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        return t;

    }
}
