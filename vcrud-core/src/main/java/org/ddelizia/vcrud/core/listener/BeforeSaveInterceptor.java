package org.ddelizia.vcrud.core.listener;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.model.basic.VcrudItem;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:34
 * To change this template use File | Settings | File Templates.
 */
public interface BeforeSaveInterceptor <T extends VcrudItem> extends VcrudInterceptor{

    public void onBeforeSave(T source, DBObject dbo);
}
