package org.ddelizia.vcrud.core.factory.modelproperty;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/29/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModelPropertyBuilderStringConstructor implements ModelPropertyBuilder{

    public Object build(Class<?> propertyClass, String data, String[] attributes){
        Constructor constructor = null;
        try {
            constructor = propertyClass.getConstructor(String.class);
            return constructor.newInstance(data);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;

    }
}
