package org.ddelizia.vcrud.model.interceprtor;

import java.util.Comparator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorComparator<T extends Interceptor> implements Comparator<String> {

    Map<String, T> base;
    public InterceptorComparator(Map<String, T> base) {
        this.base = base;
    }

    // Note: this comparator imposes orderings that are inconsistent with equals.
    public int compare(String a, String b) {
        if (base.get(a).getOrder() == null){

        }
        if (base.get(a).getOrder() >= base.get(b).getOrder()) {
            return -1;
        } else {
            return 1;
        } // returning 0 would merge keys
    }
}
