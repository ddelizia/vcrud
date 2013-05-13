package org.ddelizia.vcrud.core.test;

import junit.framework.TestCase;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.model.Type;
import org.ddelizia.vcrud.core.service.ModelService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(profiles = "test")
@ContextConfiguration(locations = "classpath:/META-INF/applicationContext-core.xml")
public class ModelTest extends TestCase {
    private static final Logger LOGGER = Logger.getLogger(ModelTest.class);


    @Autowired
    private ModelService modelService;

    @Autowired
    Environment environment;

    private Type prepareData(){
        Type type=new Type();
        type.setClazz("class");
        type.setCode("class");

        return type;
    }

    @Test
    public void createObject(){
        Type type=prepareData();
        modelService.rapidPersist(type);
        type.setSimpleClazzName("lalalala");
        modelService.rapidPersist(type);
        LOGGER.debug("-------------------------------------------------------------------------------------------------------");
        LOGGER.info("-------------------------------------------------------------------------------------------------------");
        LOGGER.warn("-------------------------------------------------------------------------------------------------------");
        LOGGER.error("-------------------------------------------------------------------------------------------------------");
        assertTrue(type.getId() != null);
    }


}
