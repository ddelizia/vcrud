package org.ddelizia.vcrud.core.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public interface Dao<E extends Object> {

    public void persist(E entity);

    public void remove(E entity);

    public E findById(Integer id);

    public List<E> findAll();

    //public abstract E create();

    public E create(Class<? extends E> clazzImpl);
}
