package org.ddelizia.vcrud.model.interceprtor;

import org.ddelizia.vcrud.model.system.VcrudModel;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:33
 * To change this template use File | Settings | File Templates.
 */
public abstract class InterceptorPrePersist extends Interceptor{

    public abstract void execute(VcrudModel vcrudModelOld, VcrudModel vcrudModelNew);

}
