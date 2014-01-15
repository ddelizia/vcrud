package org.ddelizia.vcrud.core.listener;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.model.basic.VcrudItem;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 14/01/14
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
public abstract class AfterConvertIntereptor<T extends VcrudItem> {

    private int order;

    public abstract void onAfterConvert(DBObject dbo, T source);

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
