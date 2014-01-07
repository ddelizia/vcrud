package org.ddelizia.vcrud.model.multitenancy;

import org.apache.commons.collections.CollectionUtils;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.ddelizia.vcrud.model.system.mongo.TenantMongo;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class TenantBeanFactory {

    private TenantContextWrapper tenantContextWrapper;

    public MongoFactoryBean getOrCreateBeanMongoFactoryBean(TenantHostMongo tenantHost){
        MongoFactoryBean  mongoFactoryBean =  tenantContextWrapper.getAppContext().getBean(tenantHost.getCode(), MongoFactoryBean.class);

        if (mongoFactoryBean == null){
            BeanDefinitionRegistry registry = ((BeanDefinitionRegistry)tenantContextWrapper.getFactory());

            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(MongoFactoryBean.class);
            beanDefinition.setLazyInit(false);
            beanDefinition.setAbstract(false);
            beanDefinition.setAutowireCandidate(false);
            beanDefinition.setScope("singleton");

            MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
            mutablePropertyValues.addPropertyValue("host", tenantHost.getHost());
            mutablePropertyValues.addPropertyValue("port", tenantHost.getPort());
            if (!CollectionUtils.isEmpty(tenantHost.getReplicaPair())){
                mutablePropertyValues.addPropertyValue("replicaPair", tenantHost.getReplicaPair());
            }
            if (!CollectionUtils.isEmpty(tenantHost.getReplicaSetSeeds())){
                mutablePropertyValues.addPropertyValue("replicaSetSeeds", tenantHost.getReplicaSetSeeds());
            }
            mutablePropertyValues.addPropertyValue("writeConcern", tenantHost.getWriteConcern());
            mutablePropertyValues.addPropertyValue("mongoOptions", tenantHost.getMongoOptions());
            beanDefinition.setPropertyValues(mutablePropertyValues);

            registry.registerBeanDefinition(tenantHost.getCode() , beanDefinition);

            mongoFactoryBean =  tenantContextWrapper.getAppContext().getBean(tenantHost.getCode(), MongoFactoryBean.class);
        }

        return mongoFactoryBean;
    }

    public MongoTemplate getOrCreateBeanMongoTemplate(Tenant tenant, MongoFactoryBean mongoFactoryBean) throws Exception {
        MongoTemplate mongoTemplate = tenantContextWrapper.getAppContext().getBean("test", MongoTemplate.class);

        if (mongoTemplate == null){
            BeanDefinitionRegistry registry = ((BeanDefinitionRegistry)tenantContextWrapper.getFactory());

            GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
            beanDefinition.setBeanClass(MongoTemplate.class);
            beanDefinition.setLazyInit(false);
            beanDefinition.setAbstract(false);
            beanDefinition.setAutowireCandidate(false);
            beanDefinition.setScope("singleton");

            ConstructorArgumentValues constructorArgumentValues =
                    new ConstructorArgumentValues();
            constructorArgumentValues.addIndexedArgumentValue(0, mongoFactoryBean.getObject());
            constructorArgumentValues.addIndexedArgumentValue(1, tenant.getDbName());
            if (!StringUtils.isEmpty(tenant.getUsername())){
                constructorArgumentValues.addIndexedArgumentValue(2, new UserCredentials(tenant.getUsername(), tenant.getPassword()));
            }

            registry.registerBeanDefinition(tenant.getCode() , beanDefinition);

            mongoTemplate = tenantContextWrapper.getAppContext().getBean(tenant.getCode(), MongoTemplate.class);
        }

        return mongoTemplate;
    }


    public TenantContextWrapper getTenantContextWrapper() {
        return tenantContextWrapper;
    }

    public void setTenantContextWrapper(TenantContextWrapper tenantContextWrapper) {
        this.tenantContextWrapper = tenantContextWrapper;
    }

}
