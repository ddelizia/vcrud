package org.ddelizia.vcrud.core.test.interceptor.instances;

import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.usermanagement.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class BeforeConvertInterceptorUser implements BeforeConvertInterceptor<User>{

    @Override
    public void onBeforeConvert(User source) {
        //source.setEmail();
    }
}
