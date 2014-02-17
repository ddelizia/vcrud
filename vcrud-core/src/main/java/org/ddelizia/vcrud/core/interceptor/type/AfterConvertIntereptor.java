package org.ddelizia.vcrud.core.interceptor.type;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.ddelizia.vcrud.core.interceptor.VcrudInterceptor;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 14/01/14
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public interface AfterConvertIntereptor<T extends VcrudItem> extends VcrudInterceptor {

    public abstract void onAfterConvert(DBObject dbo, T source);

}
