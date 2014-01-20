package org.ddelizia.vcrud.core.interceptor.type;

import org.ddelizia.vcrud.core.interceptor.VcrudInterceptor;
import org.ddelizia.vcrud.model.basic.VcrudItem;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 20/01/14
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public interface BeforeConvertInterceptor<T extends VcrudItem> extends VcrudInterceptor {

    public void onBeforeConvert(T source);

}
