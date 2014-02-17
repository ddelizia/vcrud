package org.ddelizia.vcrud.core.interceptor.type;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.ddelizia.vcrud.core.interceptor.VcrudInterceptor;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface BeforeSaveInterceptor <T extends VcrudItem> extends VcrudInterceptor {

    public void onBeforeSave(T source, DBObject dbo);
}
