package org.ddelizia.vcrud.core.factory.modelproperty;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/29/13
 * Time: 7:08 PM
 * To change this template use File | Settings | File Templates.
 */

public interface ModelPropertyBuilder {

    public Object build(Class<?> propertyClass, String data, String[] attributes);
}
