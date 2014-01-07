package org.ddelizia.vcrud.test;


import org.ddelizia.vcrud.model.multitenancy.service.VcrudTenantContextService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class TenantTest extends AbstractJunit4Vcrud{

    @Autowired
    @Qualifier (value = "vcudTenantContextTest")
    private VcrudTenantContextService vcrudTenantContextService;

    @Test
    public void testTenant(){
        Assert.assertNotNull(vcrudTenantContextService);
        vcrudTenantContextService.registerTenant("db","name");
        System.out.println("test");
    }

    @Override
    public void vcrudAfter() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void vcrudBefore() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
