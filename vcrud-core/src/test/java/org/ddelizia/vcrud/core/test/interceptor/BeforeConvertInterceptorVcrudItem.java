package org.ddelizia.vcrud.core.test.interceptor;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;
import org.ddelizia.vcrud.core.interceptor.type.BeforeSaveInterceptor;
import org.ddelizia.vcrud.model.basic.VcrudItem;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class BeforeConvertInterceptorVcrudItem implements BeforeConvertInterceptor<VcrudItem> {

    @Override
    public void onBeforeConvert(VcrudItem source) {
        //do nothing
    }
}
