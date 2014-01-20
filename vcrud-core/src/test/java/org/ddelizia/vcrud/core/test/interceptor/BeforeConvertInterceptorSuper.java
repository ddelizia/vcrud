package org.ddelizia.vcrud.core.test.interceptor;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.interceptor.type.BeforeSaveInterceptor;
import org.ddelizia.vcrud.core.test.InterceptorTests;
import org.ddelizia.vcrud.core.test.model.Super;

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
        source.setCode(InterceptorTests.SUPER_VALUE);
    }
}
