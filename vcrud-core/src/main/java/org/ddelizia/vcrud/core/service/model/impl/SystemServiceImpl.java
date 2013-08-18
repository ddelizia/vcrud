package org.ddelizia.vcrud.core.service.model.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.model.SystemService;
import org.ddelizia.vcrud.model.Property;
import org.ddelizia.vcrud.model.Type;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
@Service("VcrudSystemService")
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ModelService modelService;

    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger LOGGER = Logger.getLogger(SystemServiceImpl.class);
    
    @Override
    @Transactional
    public void rebuildTypeSystem() {
        LOGGER.info("Removing all Types & Properties");
        modelService.removeAll(Property.class);
        modelService.removeAll(Type.class);
        SessionFactory sessionFactory = ((HibernateEntityManager) entityManager).getSession().getSessionFactory();
        Map<String,ClassMetadata> classMetadataMap= sessionFactory.getAllClassMetadata();
        for (String key:classMetadataMap.keySet()){
            ClassMetadata value=classMetadataMap.get(key);
            AbstractEntityPersister singleTableEntityPersister = (AbstractEntityPersister) value;
            LOGGER.info("Adding class: " + singleTableEntityPersister.getMappedClass() + " with table name " + singleTableEntityPersister.getTableName());
            Type type=new Type();
            type.setCode(singleTableEntityPersister.getMappedClass().getName());
            type.setClazz(value.getMappedClass().getName());
            type.setSimpleClazzName(value.getMappedClass().getSimpleName());
            modelService.persist(type);
            LOGGER.info("Persisting: "+type.getId());

            org.hibernate.type.Type[] types=  value.getPropertyTypes();
            String[] names=value.getPropertyNames();
            for (int i=0; i<names.length; i++){
                Class clazz=types[i].getReturnedClass();
                Property property=new Property();
                property.setClazz(clazz.getName());
                property.setSimpleName(clazz.getSimpleName());
                property.setColumnName(names[i].toString());
                property.setType(type);
                property.setCode(type.getCode()+"-"+names[i].toString());
                modelService.persist(property);
                LOGGER.info("Persisting: "+property.getId());
            }



        }
    }
}
