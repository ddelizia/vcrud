package org.ddelizia.vcrud.core.test.interceptor;

import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.test.interceptor.model.Super;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class BeforeConvertInterceptorSuper implements BeforeConvertInterceptor<Super> {


    @Override
    public void onBeforeConvert(Super source) {
        //source.setCode(InterceptorTests.SUPER_VALUE);
    }
}
