package org.ddelizia.vcrud.core.dao.jpa;


import org.ddelizia.vcrud.core.dao.Dao;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class DaoJpa<E> implements Dao<E> {

    protected Class<E> entityClass;
    protected String entity;

    private static final String GENERIC_PARAM = "genericParam";

    //private static final Logger logger = LoggerFactory.getLogger(DaoJpa.class);

    @PersistenceContext
    protected EntityManager em;

    public DaoJpa() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[0];
        //logger.info(this.entityClass.toString());
    }

    @Override
    public void persist(E entity) {
        em.persist(entity);
    }

    @Override
    public void remove(E entity) {
        em.remove(entity);
    }

    @Override
    public E findById(Integer id) {
        return em.find(entityClass, id);
    }

    @Override
    public List<E> findAll() {
        Query query =
                em.createQuery("SELECT x FROM " +
                        getEntityName() + " x");
        return query.getResultList();
    }

    @Override
    public E create(Class<? extends E> clazzImpl) {
        E entity = null;
        try {
            entity = clazzImpl.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return entity;
    }

    private String getEntityName() {
        if (entity == null) {
            Entity entityAnn = entityClass.getAnnotation(Entity.class);

            if (entityAnn != null && !entityAnn.name().equals("")) {
                entity = entityAnn.name();
            } else {
                entity = entityClass.getSimpleName();
            }
        }

        return entity;
    }

    protected EntityManager getEntityManager(){
        return em;
    }

}