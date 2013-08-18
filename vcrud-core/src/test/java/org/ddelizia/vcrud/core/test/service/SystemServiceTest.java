package org.ddelizia.vcrud.core.test.service;

import junit.framework.TestCase;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.model.SystemService;
import org.ddelizia.vcrud.model.Type;
import org.ddelizia.vcrud.model.Type_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/17/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/vcrudApplicationContext-*.xml", "classpath:/META-INF/testContext-core.xml"})
public class SystemServiceTest{

    @Autowired
    private SystemService systemService;

    @Autowired
    private ModelService modelService;

    private static final Logger LOGGER = Logger.getLogger(SystemServiceTest.class);
    
    @Test
    public void testImport(){
        systemService.rebuildTypeSystem();

        Type type = modelService.getModel(Type_.clazz.getName(), User.class.getName(), Type.class);

        assert type!=null;

    }
}
