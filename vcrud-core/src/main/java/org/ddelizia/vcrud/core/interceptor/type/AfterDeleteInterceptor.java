package org.ddelizia.vcrud.core.interceptor.type;

import com.mongodb.DBObject;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 20/01/14
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public interface AfterDeleteInterceptor {

    public void onAfterDelete(DBObject dbo);

}
