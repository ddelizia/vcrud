package org.ddelizia.vcrud.model.multitenancy.service;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public interface VcrudTenantContextService {

    void registerTenant(String dbName, String name, String pattern);

    void initTenants();

    MongoTemplate getTenant (String pattern, Class<VcrudItem> vcrudItemClass);

}
