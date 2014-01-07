package org.ddelizia.vcrud.model.multitenancy.service.mongo;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.basic.VcrudTenantItem;
import org.ddelizia.vcrud.model.multitenancy.TenantBeanFactory;
import org.ddelizia.vcrud.model.multitenancy.service.VcrudTenantContextService;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class VcrudTenantContextServiceMongo implements VcrudTenantContextService{

    private String dbTenantDefaultPort;

    private String dbTenantDefaultHost;

    @Autowired
    private ApplicationContext appContext;

    private MongoTemplate basicMongoTemplate;

    private TenantBeanFactory tenantBeanFactory;

    private Map<String, MongoTemplate> cache;

    public void registerTenant(String hostname,
                               Integer port,
                               String dbName,
                               String username,
                               String password) throws Exception {

        TenantHostMongo tenantHostMongo = new TenantHostMongo();
        tenantHostMongo.setCode(calculateUUIDCode(tenantHostMongo));
        tenantHostMongo.setHost(hostname);
        tenantHostMongo.setPort(port);

        Tenant tenant = new Tenant();
        tenant.setCode(calculateUUIDCode(tenant));
        tenant.setDbName(dbName);
        tenant.setUsername(username);
        tenant.setPassword(password);

        tenantBeanFactory.getOrCreateBeanMongoTemplate(
                tenant,
                tenantBeanFactory.getOrCreateBeanMongoFactoryBean(tenantHostMongo));

        basicMongoTemplate.save(tenant);
        basicMongoTemplate.save(tenantHostMongo);
    }

    private String calculateUUIDCode(Object o){
        return UUID.randomUUID()+"_"+o.getClass();
    }

    public void initTenants(){
        List<Tenant> tenants = basicMongoTemplate.findAll(Tenant.class);
        for(Tenant tenant : tenants){
            try{
                if (tenant.getTenantHost() instanceof TenantHostMongo){
                    tenantBeanFactory.getOrCreateBeanMongoTemplate(
                            tenant, tenantBeanFactory.getOrCreateBeanMongoFactoryBean((TenantHostMongo)tenant.getTenantHost()));
                }
            }catch (Exception e){

            }
        }
    }


    public MongoTemplate getTenant (Class<VcrudItem> vcrudItemClass){

        //TODO get current session


        if(!vcrudItemClass.isAssignableFrom(VcrudTenantItem.class)){
            return basicMongoTemplate;
        }
        if (cache.containsKey(pattern)){
            return cache.get(pattern);
        }
        //TODO to be improoved with correct query
        List<Tenant> tenants = basicMongoTemplate.findAll(Tenant.class);
        Tenant correctTenant = null;
        for(Tenant tenant : tenants){
            if (pattern.matches(tenant.getPattern())){
                MongoTemplate mongoTemplate = appContext.getBean(tenant.getCode(), MongoTemplate.class);
                cache.put(pattern, mongoTemplate);
                return mongoTemplate;
            }
        }
        return null;
    }

    public void setTenantBeanFactory(TenantBeanFactory tenantBeanFactory) {
        this.tenantBeanFactory = tenantBeanFactory;
    }

    public void setBasicMongoTemplate(MongoTemplate basicMongoTemplate) {
        this.basicMongoTemplate = basicMongoTemplate;
    }

}
