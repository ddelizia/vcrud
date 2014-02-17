package org.ddelizia.vcrud.core.test.interceptor;

import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.ddelizia.vcrud.core.interceptor.type.BeforeConvertInterceptor;

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
