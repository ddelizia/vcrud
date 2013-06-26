package org.ddelizia.vcrud.model.interceprtor.impl;

import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.interceprtor.InterceptorExecutor;
import org.ddelizia.vcrud.model.interceprtor.InterceptorMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 18:10
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorMapperImpl implements InterceptorMapper{

    Map<Class<? extends VcrudModel>,InterceptorExecutor> map = new HashMap<Class<? extends VcrudModel>, InterceptorExecutor>();

}
