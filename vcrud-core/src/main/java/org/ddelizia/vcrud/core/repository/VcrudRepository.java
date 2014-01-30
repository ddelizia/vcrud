package org.ddelizia.vcrud.core.repository;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 21/01/14
 * Time: 20:44
 * To change this template use File | Settings | File Templates.
 */
public class VcrudRepository<T extends VcrudItem,ID extends Serializable> implements MongoRepository<T, ID> {

    @Override
    public <S extends T> S save(S s) {

        MongoTemplate mongoTemplate=null;
        //DateTimeProvider
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <S extends T> List<S> save(Iterable<S> ses) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public T findOne(ID id) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean exists(ID id) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<T> findAll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterable<T> findAll(Iterable<ID> ids) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public long count() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(ID id) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(T t) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Iterable<? extends T> ts) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAll() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<T> findAll(Sort orders) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
