package org.ddelizia.vcrud.core.test.service;

import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.ddelizia.vcrud.model.usermanagement.Role;
import org.ddelizia.vcrud.model.usermanagement.Role_;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 22/08/13
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 */
public class ModelServiceTest extends AbstractJunit4Vcrud{

    @Override
    public void vcrudBefore() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Test
    public void createObject(){
        Role r1 = getModelService().create(Role.class);
        r1.setCode("code");
        getModelService().rapidMerge(r1);
        Role r2 = getModelService().getModel(Role_.code.getName(),r1.getCode(),Role.class);
        Assert.assertNotNull(r2);
        //Assert.assertEquals(r1.getId(),r2.getId());
    }
}
