package org.ddelizia.vcrud.core.listener;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 18:03
 * To change this template use File | Settings | File Templates.
 */
public class InterceptorWrapper implements Comparable<InterceptorWrapper>{

    private Integer order = 0;

    private VcrudInterceptor interceptor;

    public InterceptorWrapper() {
    }

    public InterceptorWrapper(int order, VcrudInterceptor interceptor) {
        this.order = order;
        this.interceptor = interceptor;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public VcrudInterceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(VcrudInterceptor interceptor) {
        this.interceptor = interceptor;
    }


    @Override
    public int compareTo(InterceptorWrapper o) {
        return getOrder().compareTo(o.getOrder());
    }
}
