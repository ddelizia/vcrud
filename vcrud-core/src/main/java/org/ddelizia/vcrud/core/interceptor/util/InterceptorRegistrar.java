package org.ddelizia.vcrud.core.interceptor.util;

import org.ddelizia.vcrud.core.basic.collection.map.MappingRegistrar;
import org.ddelizia.vcrud.core.basic.collection.map.MappingRegistry;
import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 17/01/14
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorRegistrar extends MappingRegistrar<Class<? extends VcrudItem>, InterceptorWrapper> {

    private int order=-1;

    @Autowired
    public InterceptorRegistrar(MappingRegistry<Class<? extends VcrudItem>,InterceptorWrapper> registry) {
        super(registry);
    }

    @PostConstruct
    public void registerMapping() {
        super.registerMapping();
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
