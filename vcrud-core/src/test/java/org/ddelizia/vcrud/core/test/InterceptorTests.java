package org.ddelizia.vcrud.core.test;

import org.ddelizia.vcrud.core.test.model.Sub;
import org.ddelizia.vcrud.core.test.model.Super;
import org.ddelizia.vcrud.model.basic.LocalizedString;
import org.ddelizia.vcrud.model.multitenancy.service.VcrudTenantContextService;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.Website;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.ddelizia.vcrud.test.AbstractJunit4Vcrud;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 19/01/14
 * Time: 11:47
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorTests extends AbstractJunit4Vcrud {

    @Autowired
    private VcrudTenantContextService vcrudTenantContextService;

    private Website website1 = null;
    private Website website2 = null;

    public final static String SUB_VALUE = "test";

    public final static String SUPER_VALUE = "test1";

    @Override
    public void vcrudAfter() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void vcrudBefore() {
        TenantHostMongo tenantHostMongo1 = new TenantHostMongo(
                "codeTenantHost1",
                "localhost",
                27017,
                null,
                null,
                null,
                null
        );
        vcrudTenantContextService.getBasicMongoTemplate().save(tenantHostMongo1);

        Tenant tenant1 = new Tenant(
                "codeTenant1",
                "db1",
                null,
                null,
                tenantHostMongo1
        );
        vcrudTenantContextService.getBasicMongoTemplate().save(tenant1);

        website1 =  new Website("website1",
                "website1",
                tenant1);
        website1.getName().addLocalizedString(new LocalizedString(Locale.ITALIAN, "website1"));


        TenantHostMongo tenantHostMongo2 = new TenantHostMongo(
                "codeTenantHost2",
                "localhost",
                27017,
                null,
                null,
                null,
                null
        );
        vcrudTenantContextService.getBasicMongoTemplate().save(tenantHostMongo2);

        Tenant tenant2 = new Tenant(
                "codeTenant2",
                "db2",
                null,
                null,
                tenantHostMongo2
        );
        vcrudTenantContextService.getBasicMongoTemplate().save(tenant2);

        website2 =  new Website("website2",
                "website2",
                tenant2);
        website2.getName().addLocalizedString(new LocalizedString(Locale.ITALIAN, "website2"));

        vcrudTenantContextService.getBasicMongoTemplate().save(website1);
        vcrudTenantContextService.getBasicMongoTemplate().save(website2);
    }

    @Test
    public void interceptorTest(){

        Super theSuper = new Super();
        vcrudTenantContextService.getTenantForWebsite(website1, Super.class).save(theSuper);

        Super superResult = vcrudTenantContextService.getTenantForWebsite(website1, Super.class).findOne(
                new Query(Criteria.where("code").is(SUPER_VALUE)),
                Super.class);

        Assert.assertNotNull(superResult);

        Sub theSub = new Sub();
        vcrudTenantContextService.getTenantForWebsite(website1, Sub.class).save(theSub);

        Sub subResult = vcrudTenantContextService.getTenantForWebsite(website1, Sub.class).findOne(
                new Query(Criteria.where("code").is(SUB_VALUE)),
                Sub.class);

        Assert.assertNotNull(subResult);
        Assert.assertEquals(subResult.getCreation(),subResult.getLastModification());

        //doing modification
        subResult.setI(2);
        vcrudTenantContextService.getTenantForWebsite(website1, Sub.class).save(subResult);
        Assert.assertNotEquals(subResult.getCreation(),subResult.getLastModification());

        //remove both instances
        vcrudTenantContextService.getTenantForWebsite(website1, Sub.class).remove(theSub);
        vcrudTenantContextService.getTenantForWebsite(website1, Sub.class).remove(theSuper);
    }
}
