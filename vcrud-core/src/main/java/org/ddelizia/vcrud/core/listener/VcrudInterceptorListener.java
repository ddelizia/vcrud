package org.ddelizia.vcrud.core.listener;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.basic.provider.ApplicationContextProvider;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.MongoMappingEvent;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 13/01/14
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class VcrudInterceptorListener<T extends VcrudItem> extends AbstractMongoEventListener<T>{

    private Map<Class<T>, List<AfterConvertIntereptor>> afterConvertIntereptorMap;
    private boolean afterConvertIntereptorMapOrdrered;



    @Override
    public void onAfterConvert(DBObject dbo, T source) {
        super.onAfterConvert(dbo, source);
        ApplicationContextProvider.getApplicationContext();

    }

    @Override
    public void onBeforeSave(T source, DBObject dbo) {
        super.onBeforeSave(source, dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAfterSave(T source, DBObject dbo) {
        super.onAfterSave(source, dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAfterDelete(DBObject dbo) {
        super.onAfterDelete(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onAfterLoad(DBObject dbo) {
        super.onAfterLoad(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onApplicationEvent(MongoMappingEvent<?> event) {
        super.onApplicationEvent(event);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onBeforeConvert(T source) {
        super.onBeforeConvert(source);    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public void onBeforeDelete(DBObject dbo) {
        super.onBeforeDelete(dbo);    //To change body of overridden methods use File | Settings | File Templates.
    }

}

