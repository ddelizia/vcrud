package org.ddelizia.vcrud.model.multitenancy;

import com.mongodb.Mongo;
import org.apache.commons.collections.CollectionUtils;
import org.ddelizia.vcrud.basic.provider.ApplicationContextProvider;
import org.ddelizia.vcrud.model.system.Tenant;
import org.ddelizia.vcrud.model.system.mongo.TenantHostMongo;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoFactoryBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class TenantBeanFactory {

    public Mongo getOrCreateBeanMongo(TenantHostMongo tenantHost){
        Mongo mongoBean=null;
        try {
            mongoBean = ApplicationContextProvider.getBean(tenantHost.getCode(), Mongo.class);
        }catch (NoSuchBeanDefinitionException e){

            if (mongoBean == null){
                BeanDefinitionRegistry registry = ((BeanDefinitionRegistry)ApplicationContextProvider.getBeanFactory());

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

                registry.registerBeanDefinition(tenantHost.getCode(), beanDefinition);

                mongoBean = ApplicationContextProvider.getBean(tenantHost.getCode(), Mongo.class);

                //ApplicationContextProvider.getApplicationContext().r
            }
        }

        return mongoBean;
    }

    public MongoTemplate getOrCreateBeanMongoTemplate(Tenant tenant, Mongo mongo) throws Exception {
        MongoTemplate mongoTemplate = null;
        try {
            mongoTemplate = ApplicationContextProvider.getBean(tenant.getCode(), MongoTemplate.class);
        }catch (NoSuchBeanDefinitionException e){
            if (mongoTemplate == null){
                BeanDefinitionRegistry registry = ((BeanDefinitionRegistry)ApplicationContextProvider.getBeanFactory());

                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClass(MongoTemplate.class);
                beanDefinition.setLazyInit(false);
                beanDefinition.setAbstract(false);
                beanDefinition.setAutowireCandidate(false);
                beanDefinition.setScope("singleton");

                ConstructorArgumentValues constructorArgumentValues =
                        new ConstructorArgumentValues();
                constructorArgumentValues.addIndexedArgumentValue(0, mongo);
                constructorArgumentValues.addIndexedArgumentValue(1, tenant.getDbName());
                if (!StringUtils.isEmpty(tenant.getUsername())){
                    constructorArgumentValues.addIndexedArgumentValue(2, new UserCredentials(tenant.getUsername(), tenant.getPassword()));
                }
                beanDefinition.setConstructorArgumentValues(constructorArgumentValues);

                registry.registerBeanDefinition(tenant.getCode() , beanDefinition);

                mongoTemplate = ApplicationContextProvider.getBean(tenant.getCode(), MongoTemplate.class);
            }
        }

        return mongoTemplate;
    }

}
