package org.ddelizia.vcrud.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 19/08/13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
        "classpath*:/META-INF/spring/vcrudApplicationContext-*.xml",
        "classpath*:/META-INF/vcrudApplicationContextTest-*.xml"})
@ActiveProfiles(value = "test")
public abstract class AbstractJunit4Vcrud {

    @Before
    public void before(){
        vcrudBefore();
    }

    @After
    public void after(){
        vcrudAfter();
    }

    /**
     * Implement this method instead of @After
     */
    public abstract void vcrudAfter();

    /**
     * Implement this method instead of @Before
     */
    public abstract void vcrudBefore();

}
