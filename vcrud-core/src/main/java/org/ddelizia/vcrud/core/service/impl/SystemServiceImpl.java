package org.ddelizia.vcrud.core.service.impl;

import org.ddelizia.vcrud.core.model.Property;
import org.ddelizia.vcrud.core.model.Type;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.core.service.SystemService;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 17:18
 * To change this template use File | Settings | File Templates.
 */
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ModelService modelService;

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    @Transactional
    public void rebuildTypeSystem() {
        modelService.removeAll(Type.class);
        SessionFactory sessionFactory = ((HibernateEntityManager) entityManager).getSession().getSessionFactory();
        Map<String,ClassMetadata> classMetadataMap= sessionFactory.getAllClassMetadata();
        for (String key:classMetadataMap.keySet()){
            ClassMetadata value=classMetadataMap.get(key);
            AbstractEntityPersister singleTableEntityPersister = (AbstractEntityPersister) value;
            Type type=new Type();
            type.setCode(singleTableEntityPersister.getTableName());
            type.setClazz(value.getMappedClass().getName());
            type.setSimpleClazzName(value.getMappedClass().getSimpleName());
            modelService.persist(type);

            org.hibernate.type.Type[] types=  value.getPropertyTypes();
            String[] names=value.getPropertyNames();
            for (int i=0; i<names.length; i++){
                Class clazz=types[i].getReturnedClass();
                Property property=new Property();
                property.setClazz(clazz.getName());
                property.setSimpleName(clazz.getSimpleName());
                property.setColumnName(names[i].toString());
                property.setType(type);
                property.setCode(clazz.getName()+"-"+names[i].toString());
                modelService.persist(property);
            }



        }
    }
}
