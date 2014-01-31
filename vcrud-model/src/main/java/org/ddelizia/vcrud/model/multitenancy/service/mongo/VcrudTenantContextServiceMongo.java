package org.ddelizia.vcrud.model.multitenancy.service.mongo;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.basic.provider.RequestProvider;
import org.ddelizia.vcrud.basic.utils.CodeGenerator;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.basic.VcrudTenantItem;
import org.ddelizia.vcrud.model.multitenancy.TenantBeanFactory;
import org.ddelizia.vcrud.model.multitenancy.service.VcrudTenantContextService;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.Website;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
public class VcrudTenantContextServiceMongo implements VcrudTenantContextService{

    private MongoTemplate basicMongoTemplate;

    private TenantBeanFactory tenantBeanFactory;

    private Map<String, Website> webSiteCache = new HashMap<>();

    Logger LOG = Logger.getLogger(VcrudTenantContextServiceMongo.class);

    public void registerTenant(String hostname,
                               Integer port,
                               String dbName,
                               String username,
                               String password) throws Exception {

        TenantHostMongo tenantHostMongo = new TenantHostMongo();
        tenantHostMongo.setCode(CodeGenerator.calculateUUIDCode(tenantHostMongo));
        tenantHostMongo.setHost(hostname);
        tenantHostMongo.setPort(port);

        Tenant tenant = new Tenant();
        tenant.setCode(CodeGenerator.calculateUUIDCode(tenant));
        tenant.setDbName(dbName);
        tenant.setUsername(username);
        tenant.setPassword(password);

        tenantBeanFactory.getOrCreateBeanMongoTemplate(
                tenant,
                tenantBeanFactory.getOrCreateBeanMongo(tenantHostMongo));

        basicMongoTemplate.save(tenant);
        basicMongoTemplate.save(tenantHostMongo);
    }

    public void initTenants(){
        List<Tenant> tenants = basicMongoTemplate.findAll(Tenant.class);
        for(Tenant tenant : tenants){
            getMongoTemplateForTenant(tenant);
        }
    }


    public MongoTemplate getTenantCurrentTenant (Class<? extends VcrudItem> vcrudItemClass){
        return getTenantForWebsite(RequestProvider.getServerName(), vcrudItemClass);
    }

    public MongoTemplate getTenantForWebsite (String serverName, Class<? extends VcrudItem> vcrudItemClass){
        Website websiteCached = webSiteCache.get(serverName);
        if (websiteCached==null){
            List<Website> websites = basicMongoTemplate.findAll(Website.class);
            for(Website website: websites){
                if(serverName.matches(website.getRegex())){
                    webSiteCache.put(serverName, website);
                    return getTenantForWebsite(website, vcrudItemClass);
                }
            }
            return null;
        }else {
            return getTenantForWebsite(websiteCached, vcrudItemClass);
        }
    }

    public MongoTemplate getTenantForWebsite (Website website, Class<? extends VcrudItem> vcrudItemClass){
        if (website == null){
            return getBasicMongoTemplate();
        }
        if (!VcrudTenantItem.class.isAssignableFrom(vcrudItemClass)){
            LOG.info("No website found fall back on basic tenant");
            return basicMongoTemplate;
        }
        return getMongoTemplateForTenant(website.getTenant());
    }

    public MongoTemplate getMongoTemplateForTenant(Tenant tenant){
        try {
            if (tenant.getTenantHost() instanceof TenantHostMongo){
                return tenantBeanFactory.getOrCreateBeanMongoTemplate(
                        tenant, tenantBeanFactory.getOrCreateBeanMongo((TenantHostMongo)tenant.getTenantHost()));
            }
        } catch (Exception e) {
            LOG.info("No session found fall back on basic tenant");
            e.printStackTrace();
            return getBasicMongoTemplate();
        }
        return null;
    }

    public void setTenantBeanFactory(TenantBeanFactory tenantBeanFactory) {
        this.tenantBeanFactory = tenantBeanFactory;
    }

    public void setBasicMongoTemplate(MongoTemplate basicMongoTemplate) {
        this.basicMongoTemplate = basicMongoTemplate;
    }

    public MongoTemplate getBasicMongoTemplate() {
        return basicMongoTemplate;
    }
}
