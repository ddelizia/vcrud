package org.ddelizia.vcrud.model.listener;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 13/01/14
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class VcrudListener<T extends VcrudItem> extends AbstractMongoEventListener<T>{

    private Class<T> aClass;

    public VcrudListener(Class<T> aClass) {
        this.aClass = aClass;
    }

    @Override
    public void onAfterConvert(DBObject dbo, T source) {
        super.onAfterConvert(dbo, source);

    }

    @Override
    public void onBeforeSave(T source, DBObject dbo) {
        super.onBeforeSave(source, dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAfterSave(T source, DBObject dbo) {
        super.onAfterSave(source, dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    public Class<T> getaClass() {
        return aClass;
    }

    public void setaClass(Class<T> aClass) {
        this.aClass = aClass;
    }
}

