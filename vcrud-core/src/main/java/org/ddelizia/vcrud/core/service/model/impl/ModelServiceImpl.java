package org.ddelizia.vcrud.core.service.model.impl;

import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
@Service("VcrudModelService")
public class ModelServiceImpl implements ModelService{

    //private static final Logger logger = LoggerFactory.getLogger(ModelServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    private static final String GENERIC_PARAM= "genericParam";
    private static final String PREFIX_PARAM= "prefixParam";

    @Override
    public <T extends VcrudModel> T create(Class<T> clazz) {
        T t = null;
        try {
            /*
            boolean freeIdFound = false;
            String generatedCode = "";
            while (freeIdFound == false){
                generatedCode = UUID.randomUUID().toString().replace("-","");
                VcrudModel vcrudModel = getModel(VcrudModel_.id.getName(),generatedCode,clazz);
                if (vcrudModel==null){
                    freeIdFound = true;
                }
            }
            */
            t = clazz.newInstance();
            //t.setId(generatedCode);
            //rapidMerge(t);
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return t;
    }

    @Override
    public <T extends VcrudModel> T getModel(String field, Object o, Class<T> clazz){

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

    @Override
    public <T extends VcrudModel> List<T> getModels(String field, Object o, Class<T> clazz, Integer from, Integer count){
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x " +
                "WHERE x."+field+"=:"+GENERIC_PARAM;

        Query query = entityManager.createQuery(queryString)
                .setParameter(GENERIC_PARAM, o);

        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
        }

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        return t;
    }

    @Override
    public <T extends VcrudModel> T getModel(Map<String, Object> map, Class<T> clazz){

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

    @Override
    public <T extends VcrudModel> List<T> getModels(Map<String, Object> map, Class<T> clazz, Integer from, Integer count){

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

        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
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
    public <T extends VcrudModel> T getModelLike(String field, Object o, Class<T> clazz) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x " +
                "WHERE UPPER(x."+field+") LIKE :"+GENERIC_PARAM;

        Query query = entityManager.createQuery(queryString).setParameter(GENERIC_PARAM, "%"+o+"%");

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

    @Override
    public <T extends VcrudModel> List<T> getModelsLike(String field, Object o, Class<T> clazz, Integer from, Integer count) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x " +
                "WHERE UPPER(x."+field+") LIKE :"+GENERIC_PARAM;

        Query query = entityManager.createQuery(queryString)
                .setParameter(GENERIC_PARAM, "%"+o+"%");

        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
        }

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No model found with field: " + field);
        }
        return t;
    }

    @Override
    public <T extends VcrudModel> T getModelLike(Map<String, Object> map, Class<T> clazz) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x ";

        int i=1;

        for (String field : map.keySet()) {
            if (i==1){
                queryString+="WHERE";
            }else if (i!=1){
                queryString+=" AND";
            }
            queryString+=" UPPER(x."+field+") LIKE :"+PREFIX_PARAM+field;
            i++;
        }

        Query query = entityManager.createQuery(queryString);
        for (String field : map.keySet()) {
            query.setParameter(PREFIX_PARAM+field, "%"+map.get(field)+"%");
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

    @Override
    public <T extends VcrudModel> List<T> getModelsLike(Map<String, Object> map, Class<T> clazz, Integer from, Integer count) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x ";

        int i=1;

        for (String field : map.keySet()) {
            if (i==1){
                queryString+="WHERE";
            }else if (i!=1){
                queryString+=" AND";
            }
            queryString+=" UPPER(x."+field+") LIKE :"+PREFIX_PARAM+field;
            i++;
        }

        Query query = entityManager.createQuery(queryString);
        for (String field : map.keySet()) {
            query.setParameter(PREFIX_PARAM+field, "%"+map.get(field)+"%");
        }

        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
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
    public <T extends VcrudModel> List<T> getModels(Class<T> clazz, Integer from, Integer count) {
        String queryString = "SELECT x " +
                "FROM " + clazz.getName() + " x";
        Query query = entityManager.createQuery(queryString);

        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
        }

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No models found");
        }
        return t;
    }

    @Override
    public <T extends VcrudModel> List<T> executeQuery(Map<String, Object> params, String queryString, Class<T> clazz, Integer from, Integer count) {
        Query query = entityManager.createQuery(queryString);
        if(params!=null){
            for (String field : params.keySet()) {
                query.setParameter(field, params.get(field));
            }
        }
        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
        }

        List<T> t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No models found");
        }
        return t;
    }

    @Override
    public List executeQueryObject(Map<String, Object> params, String queryString, Class clazz, Integer from, Integer count) {
        Query query = entityManager.createQuery(queryString);
        if(params!=null){
            for (String field : params.keySet()) {
                query.setParameter(field, params.get(field));
            }
        }
        if(from!=null){
            query.setFirstResult(from);
        }
        if(count!=null){
            query.setMaxResults(count);
        }

        List t = null;
        try {
            t = (query.getResultList());
        } catch (NoResultException e) {
            //logger.info("No models found");
        }
        return t;
    }

    @Override
    public <T extends VcrudModel> T max(Class<T> clazz, String field) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
        Root r = cq.from(clazz);
        cq.select(
                cb.greatest(
                        r.get(field)
                )
        );
        return (T)entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public <T extends VcrudModel> T min(Class<T> clazz, String field) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
        Root r = cq.from(clazz);
        cq.select(
                cb.least(
                        r.get(field)
                )
        );
        return (T)entityManager.createQuery(cq).getSingleResult();
    }

    @Override
    public int count(Class<? extends VcrudModel> clazz) {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(clazz)));
        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    @Override
    public int removeAll(Class<? extends VcrudModel> clazz) {
        return entityManager.createQuery("DELETE FROM "+ clazz.getName()).executeUpdate();
    }

    @Override
    public void remove(VcrudModel o) {
        entityManager.remove(o);
    }

    @Override
    public void persist (VcrudModel o){
        entityManager.persist(o);
    }

    @Override
    @Transactional
    public void rapidPersist(VcrudModel o) {
        entityManager.persist(o);
    }

    @Override
    public VcrudModel merge(VcrudModel o) {
        return entityManager.merge(o);
    }

    @Override
    @Transactional
    public VcrudModel rapidMerge(VcrudModel o) {
        return entityManager.merge(o);
    }

    @Override
    public Map<String, Class> getAllModelClasses() {
        SessionFactory sessionFactory = ((HibernateEntityManager) entityManager).getSession().getSessionFactory();
        Map<String, ClassMetadata> map = sessionFactory.getAllClassMetadata();
        Map<String, Class> result = new HashMap<String, Class>();
        for(String key:map.keySet()){
            ClassMetadata classMetadata = map.get(key);
            result.put(key, classMetadata.getMappedClass());
        }
        return result;
    }

    @Override
    public List<Class<? extends VcrudModel>> getAllVcrudItems() {
        Map<String, Class> map = getAllModelClasses();
        List<Class<? extends VcrudModel>> result = new ArrayList<Class<? extends VcrudModel>>();
        for(String key:map.keySet()){
            Class clazz = map.get(key);
            VcrudItem vcrudItem = (VcrudItem) clazz.getAnnotation(VcrudItem.class);
            if (vcrudItem!=null){
                result.add(clazz);
            }
        }
        return result;
    }

    @Override
    public boolean isVcrudEntity(Class clazz) {
        return VcrudModel.class.isAssignableFrom(clazz);
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
}