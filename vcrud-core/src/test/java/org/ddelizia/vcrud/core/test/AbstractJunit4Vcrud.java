package org.ddelizia.vcrud.core.test;

import org.ddelizia.vcrud.core.service.model.ImpExpService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.model.SystemService;
import org.ddelizia.vcrud.core.test.config.VcrudTestSetUp;
import org.junit.Before;
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
 * User: danilo.delizia
 * Date: 19/08/13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/META-INF/vcrudApplicationContext-*.xml", "classpath:/META-INF/testContext-core.xml"})
public abstract class AbstractJunit4Vcrud {

    @Autowired
    private SystemService systemService;

    @Autowired
    private ImpExpService impExpService;

    @Autowired
    private ModelService modelService;

    @Before
    public void before(){
        initSystem();
        vcrudBefore();
    }

    public abstract void vcrudBefore();

    public void initSystem(){
        if (!VcrudTestSetUp.isTestEnvSetUp()){

            systemService.rebuildTypeSystem();
            InputStream in = this.getClass().getResourceAsStream("/files/impDataInsert.txt");
            Reader reader = new InputStreamReader(in);
            try {
                impExpService.importData(reader);
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            VcrudTestSetUp.setTestEnvSetUp(true);
        }
    }

    public SystemService getSystemService() {
        return systemService;
    }

    public ImpExpService getImpExpService() {
        return impExpService;
    }

    public ModelService getModelService() {
        return modelService;
    }
}
