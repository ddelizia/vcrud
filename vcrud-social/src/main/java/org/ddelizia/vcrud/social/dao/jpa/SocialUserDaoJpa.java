package org.ddelizia.vcrud.social.dao.jpa;

import org.ddelizia.vcrud.core.dao.jpa.DaoJpa;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.ddelizia.vcrud.social.dao.SocialUserDao;
import org.ddelizia.vcrud.social.model.SocialUser;
import org.ddelizia.vcrud.social.model.SocialUser_;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 12/08/13
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */

@Component("vcrudSocialUserDao")
public class SocialUserDaoJpa extends DaoJpa<SocialUser> implements SocialUserDao{


    @Override
    public List<SocialUser> findIn(User user, MultiValueMap<String, String> providerUsers) {
        if (providerUsers.isEmpty()){
            return Collections.<SocialUser> emptyList();
        }

        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();

        CriteriaQuery<SocialUser> q = cb.createQuery(SocialUser.class);
        Root<SocialUser> c = q.from(SocialUser.class);


        Predicate eqUser = cb.equal(c.get(SocialUser_.user), user);

        Predicate disjPrdicate = cb.disjunction();

        for (Iterator<Map.Entry<String, List<String>>> it = providerUsers
                .entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, List<String>> entry = it.next();
            String providerId = entry.getKey();
            Path<String> path = c.get(SocialUser_.providerUserId);
            CriteriaBuilder.In<String> in = cb.in(path);
            in.value((Expression<? extends String>) entry.getValue());

            Predicate predicate =  cb.and(
                    cb.equal(c.get(org.ddelizia.vcrud.social.model.SocialUser_.providerId),providerId),
                    cb.in(in)
            );
            disjPrdicate.getExpressions().add(predicate);
        }

        Predicate finalAnd = cb.and(eqUser,disjPrdicate);

        q.select(c)
                .where(finalAnd);

        Query query = getEntityManager().createQuery(q);
        return query.getResultList();

    }

    @Override
    public Set<String> findUsersConnectedTo(String providerId, Set<String> providerUserIds) {
        String hql = "select distinct su."+SocialUser_.user.getName()+"." +User_.email.getName()+ " " +
                "from "+SocialUser.class.getName()+" su " +
                "where su.providerId = :providerId and su.providerUserId in (:providerUserIds)";

        Query query = getEntityManager().createQuery(hql)
                .setParameter("providerId", providerId)
                .setParameter("providerUserId", providerUserIds);

        return new HashSet<String>(query.getResultList());
    }

    @Override
    public List<User> findUsersModelConnectedTo(String providerId, Set<String> providerUserIds) {
        String param1="param1";
        String param2="param2";

        String queryString = "SELECT u " +
                "FROM " + SocialUser.class.getName() + " s INNER JOIN s." + SocialUser_.user + " u " +
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