package org.ddelizia.vcrud.core.service.impl;

import org.ddelizia.vcrud.core.service.ModelService;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Service("org.ddelizia.vcrud.core.service.ModelService")
public class ModelServiceImpl implements ModelService{

    //private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GENERIC_PARAM= "genericParam";
    private static final String PREFIX_PARAM= "prefixParam";

    public <T extends Object> T findByField(String field, Object o, Class <T> clazz){

        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x " +
                "WHERE x."+field+"=:"+GENERIC_PARAM;

        Query query = entityManager.createQuery(queryString).setParameter(GENERIC_PARAM, o);

        T t = null;
        try {
            t = (T) (query.getSingleResult());
        } catch (NonUniqueResultException e) {
            //logger.info("More then one model with the same field");
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        return t;
    }

    public <T extends Object> List<T> findListByField(String field, Object o, Class <T> clazz){
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x " +
                "WHERE x."+field+"=:"+GENERIC_PARAM;

        Query query = entityManager.createQuery(queryString)
                .setParameter(GENERIC_PARAM, o);

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        return t;
    }

    public <T extends Object> T findByFields(Map<String, Object> map, Class <T> clazz){

        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x ";

        int i=1;

        for (String field : map.keySet()) {
            if (i==1){
                queryString+="WHERE";
            }else if (i!=1){
                queryString+=" AND";
            }
            queryString+=" x."+field+"=:"+PREFIX_PARAM+field;
            i++;
        }

        Query query = entityManager.createQuery(queryString);
        for (String field : map.keySet()) {
            query.setParameter(PREFIX_PARAM+field, map.get(field));
        }

        T u = null;
        try {
            u = (T) (query.getSingleResult());
        } catch (NonUniqueResultException e) {
            //logger.info("More then one model with the same field");
        } catch (NoResultException e) {
            //logger.info("No model found with query: " + queryString);
        }
        return u;
    }

    public <T extends Object> List<T> findListByFields(Map<String, Object> map, Class<T> clazz){

        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x ";

        int i=1;

        for (String field : map.keySet()) {
            if (i==1){
                queryString+="WHERE";
            }else if (i!=1){
                queryString+=" AND";
            }
            queryString+=" x."+field+"=:"+PREFIX_PARAM+field;
            i++;
        }

        Query query = entityManager.createQuery(queryString);
        for (String field : map.keySet()) {
            query.setParameter(PREFIX_PARAM+field, map.get(field));
        }

        List<T> u = null;
        try {
            u = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with query: " + queryString);
        }
        return u;
    }

    @Override
    public <T extends Object> List<T> findAll(Class<T> clazz) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x";
        Query query = entityManager.createQuery(queryString);

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No models found");
        }
        return t;
    }

    @Override
    public int removeAll(Class clazz) {
        List list = this.findAll(clazz);
        for (int i=0; i<list.size(); i++){
            remove(list.get(i));
        }
        return list.size();
        //return entityManager.createQuery("DELETE FROM "+ clazz.getName()).executeUpdate();
    }

    @Override
    public void remove(Object o) {
        entityManager.remove(o);
    }

    public void persist (Object o){
        entityManager.persist(o);
    }

    @Override
    @Transactional
    public void rapidPersist(Object o) {
        entityManager.persist(o);
    }

    @Override
    public Map<String, ClassMetadata> getAllClassMetadata() {
        SessionFactory sessionFactory = ((HibernateEntityManager) entityManager).getSession().getSessionFactory();
        return sessionFactory.getAllClassMetadata();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}
