package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.ddelizia.vcrud.model.Type;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.metadata.ClassMetadata;
import org.junit.Assert;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/17/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

public class SystemServiceTest extends AbstractJunit4Vcrud{

    private static final Logger LOGGER = Logger.getLogger(SystemServiceTest.class);

    @PersistenceContext
    private EntityManager entityManager;
    
    @Test
    public void testImport(){

        List<Type> types = getModelService().getModels(Type.class,null,null);

        SessionFactory sessionFactory = ((HibernateEntityManager) entityManager).getSession().getSessionFactory();
        Map<String,ClassMetadata> classMetadataMap= sessionFactory.getAllClassMetadata();

        String sType ="Types: ";
        for (Type type: types){
            sType+="\n"+type.getClazz();
        }
        String sVcrudItems ="VcrudItems: ";
        for (String s: classMetadataMap.keySet()){
            sVcrudItems+="\n"+classMetadataMap.get(s).getMappedClass().getName();
        }
        LOGGER.info(sType);
        LOGGER.info(sVcrudItems);
        Assert.assertEquals(classMetadataMap.size(), types.size());

        org.springframework.util.Assert.noNullElements(types.toArray());

    }

    @Override
    public void vcrudBefore() {
    }
}
