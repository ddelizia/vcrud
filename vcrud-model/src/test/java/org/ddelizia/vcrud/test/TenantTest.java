package org.ddelizia.vcrud.test;


import org.ddelizia.vcrud.model.basic.LocalizedString;
import org.ddelizia.vcrud.model.multitenancy.service.VcrudTenantContextService;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.Website;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:31
 * To change this template use File | Settings | File Templates.
 */
public class TenantTest extends AbstractJunit4Vcrud{

    @Autowired
    private VcrudTenantContextService vcrudTenantContextService;

    private Website website1 = null;
    private Website website2 = null;

    private static final int NUMBER_OF_INSTANCE = 10;

    @Test
    public void testTenant(){
        MongoTemplate mongoTemplate1 = vcrudTenantContextService.getTenantForWebsite(website1, SimpleItem.class);
        MongoTemplate mongoTemplate2 = vcrudTenantContextService.getTenantForWebsite(website2,SimpleItem.class);

        List<SimpleItem> simpleItems1 = mongoTemplate1.findAll(SimpleItem.class);
        List<SimpleItem> simpleItems2 = mongoTemplate2.findAll(SimpleItem.class);

        Assert.assertEquals(
                simpleItems1.size(),
                NUMBER_OF_INSTANCE/2);
        Assert.assertEquals(
                simpleItems2.size(),
                NUMBER_OF_INSTANCE/2);

        for(SimpleItem simpleItem: simpleItems1){
            mongoTemplate1.remove(simpleItem);
        }

        for(SimpleItem simpleItem: simpleItems2){
            mongoTemplate2.remove(simpleItem);
        }


        Assert.assertEquals(
                mongoTemplate1.findAll(SimpleItem.class).size(),
                0);
        Assert.assertEquals(
                mongoTemplate2.findAll(SimpleItem.class).size(),
                0);

    }

    @Override
    public void vcrudAfter() {

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
                new LocalizedString(Locale.ITALIAN, "website1"),
                "website1",
                tenant1);


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
                new LocalizedString(Locale.ITALIAN, "website2"),
                "website2",
                tenant2);

        vcrudTenantContextService.getBasicMongoTemplate().save(website1);
        vcrudTenantContextService.getBasicMongoTemplate().save(website2);

        createSimpleItems();
    }

    private void createSimpleItems(){
        for (int i=0; i< NUMBER_OF_INSTANCE; i++){
            SimpleItem simpleItem = new SimpleItem("code"+i, i);
            if (i % 2 == 0){
                vcrudTenantContextService.getTenantForWebsite(website1,SimpleItem.class).save(simpleItem);
            }else {
                vcrudTenantContextService.getTenantForWebsite(website2,SimpleItem.class).save(simpleItem);
            }
        }
    }

}
