package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.service.model.ImpExpService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.model.SystemService;
import org.ddelizia.vcrud.model.Type;
import org.ddelizia.vcrud.model.Type_;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/17/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/vcrudApplicationContext-*.xml", "classpath:/META-INF/testContext-core.xml"})
public class ImpExpServiceTest {

    @Autowired
    private SystemService systemService;

    @Autowired
    private ImpExpService impExpService;

    @Autowired
    private ModelService modelService;

    private static final Logger LOGGER = Logger.getLogger(ImpExpServiceTest.class);

    @Before
    public void buildSystem(){
        systemService.rebuildTypeSystem();
    }

    @Test
    public void testImportFile(){
        LOGGER.info("Running " + ImpExpServiceTest.class);

        InputStream in = this.getClass()
                .getResourceAsStream("/files/impDataInsert.txt");
        Reader reader = new InputStreamReader(in);

        try {
            impExpService.importData(reader);
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        User u = modelService.getModel(User_.username.getName(), "ddelizia", User.class);

        assert u.getUsername().equals("ddelizia");
        assert u.getPassword().equals("ddelizia");
        assert u.getEmail().equals("ddelizia");
    }
}
