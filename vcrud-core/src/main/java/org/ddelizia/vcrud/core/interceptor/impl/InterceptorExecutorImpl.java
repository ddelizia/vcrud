package org.ddelizia.vcrud.core.interceptor.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.ddelizia.vcrud.core.config.ApplicationContextProvider;
import org.ddelizia.vcrud.core.interceptor.Interceptor;
import org.ddelizia.vcrud.core.interceptor.InterceptorExecutor;
import org.ddelizia.vcrud.core.interceptor.InterceptorOnFlushDirty;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorExecutorImpl extends EmptyInterceptor implements InterceptorExecutor {

    private static Log LOG =  LogFactory.getLog(InterceptorExecutorImpl.class);

    @Autowired
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, Type[] types) {
        Map<String,InterceptorOnFlushDirty> interceptors = Interceptor.orderInterceptors(applicationContextProvider.getApplicationContext().getBeansOfType(InterceptorOnFlushDirty.class));
        for (String key: interceptors.keySet()){
            interceptors.get(key).execute();
        }

        LOG.debug("Here I'm in the onFlush");
        return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
    }


}
