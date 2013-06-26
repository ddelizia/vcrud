package org.ddelizia.vcrud.model.interceprtor;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:39
 * To change this template use File | Settings | File Templates.
 */
public abstract class Interceptor {

    private Integer order;

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public static <T extends Interceptor> TreeMap<String,T> orderInterceptors(Map<String,T> map){
        InterceptorComparator<T> bvc =  new InterceptorComparator<T>(map);
        return new TreeMap<String,T>(bvc);
    }


}
