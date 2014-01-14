package org.ddelizia.vcrud.model.multitenancy.service;

import org.ddelizia.vcrud.basic.provider.RequestProvider;
import org.ddelizia.vcrud.basic.utils.CodeGenerator;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.Website;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public interface VcrudTenantContextService {

    public void registerTenant(String hostname,
                               Integer port,
                               String dbName,
                               String username,
                               String password) throws Exception;

    public void initTenants();


    public MongoTemplate getTenantCurrentTenant (Class<? extends VcrudItem> vcrudItemClass);

    public MongoTemplate getTenantForWebsite (String serverName, Class<? extends VcrudItem> vcrudItemClass);

    public MongoTemplate getTenantForWebsite (Website website, Class<? extends VcrudItem> vcrudItemClass);

    public MongoTemplate getMongoTemplateForTenant(Tenant tenant);

    public MongoTemplate getBasicMongoTemplate();

}
