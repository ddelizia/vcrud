package org.ddelizia.vcrud.core.test.interceptor;

import com.mongodb.DBObject;
import org.ddelizia.vcrud.core.listener.BeforeSaveInterceptor;
import org.ddelizia.vcrud.core.test.model.Super;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:38
 * To change this template use File | Settings | File Templates.
 */
public class BeforeSaveInterceptorSuper implements BeforeSaveInterceptor<Super>{

    @Override
    public void onBeforeSave(Super source, DBObject dbo) {
        System.out.println("INTERCEPROR LAUNCHED");
    }
}
