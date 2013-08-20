package org.ddelizia.vcrud.core.dao.jpa;

import org.ddelizia.vcrud.core.dao.SocialUserDao;
import org.ddelizia.vcrud.model.social.SocialUser;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.NoResultException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */

@Component("org.ddelizia.vcrud.core.dao.jpa.UserDaoJpa")
public class SocialUserDaoJpa extends DaoJpa<SocialUser> implements SocialUserDao{


    @Override
    public List<SocialUser> findIn(String userId, MultiValueMap<String, String> providerUsers) {
        String prefixParam = "pref";
        String userIdParam = "pref";

        String queryString = "SELECT su " +
                "FROM " + User.class.getName() + " u INNER JOIN s." + User_.socialUsers + " su ";

        int i=1;

        for (String field : providerUsers.keySet()) {
            if (i==1){
                queryString+="WHERE u."+User_.username.getName()+" = :"+userIdParam +" AND " ;
            }else if (i!=1){
                queryString+=" OR";
            }
            queryString+=" x."+field+"=:"+prefixParam+field;
            i++;
        }


        //Query query = getEntityManager().createQuery(queryString)
                //.setParameter(userIdParam, providerId)
                //.setParameter(param2, providerUserIds);;

        List<User> t = null;
        try {
            //t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        //return t;
        return  null;
    }
}
