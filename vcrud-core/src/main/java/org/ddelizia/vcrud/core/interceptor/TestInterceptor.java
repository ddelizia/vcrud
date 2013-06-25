package org.ddelizia.vcrud.core.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 13/05/13
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
public class TestInterceptor extends InterceptorOnFlushDirty {

    private static Log LOG =  LogFactory.getLog(TestInterceptor.class);

    @Override
    public void execute() {
        LOG.debug("Here is running the interceptor");
    }
}
