package org.ddelizia.vcrud.core.service;

import org.hibernate.metadata.ClassMetadata;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:21
 * To change this template use File | Settings | File Templates.
 */
public interface ModelService {

    public <T extends Object> T findByField(String field, Object o, Class <T> clazz);

    public <T extends Object> List<T> findListByField(String field, Object o, Class <T> clazz);

    public <T extends Object> T findByFields(Map<String, Object> map, Class <T> clazz);

    public <T extends Object> List<T> findListByFields(Map<String, Object> map, Class<T> clazz);

    public <T extends Object> List<T> findAll(Class<T> clazz);

    public int removeAll (Class clazz);

    public void remove (Object o);

    public void persist (Object o);

    public void rapidPersist (Object o);

    public Map<String,ClassMetadata> getAllClassMetadata();
}
