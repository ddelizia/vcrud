package org.ddelizia.vcrud.core.test.interceptor;

import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.test.interceptor.model.Sub;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class BeforeConvertInterceptorSub implements BeforeConvertInterceptor<Sub>{

    @Override
    public void onBeforeConvert(Sub source) {
        //source.setCode(InterceptorTests.SUB_VALUE);
    }
}
