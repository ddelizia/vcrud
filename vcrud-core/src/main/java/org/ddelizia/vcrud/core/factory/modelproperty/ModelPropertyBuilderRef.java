package org.ddelizia.vcrud.core.factory.modelproperty;

import org.apache.commons.beanutils.PropertyUtils;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.system.Property_;
import org.ddelizia.vcrud.model.system.Type;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/29/13
 * Time: 9:38 PM
 * To change this template use File | Settings | File Templates.
 */

public class ModelPropertyBuilderRef implements ModelPropertyBuilder{

    @Autowired
    private ModelService modelService;

    @Override
    public Object build(Class<?> propertyClass, String data, String[] attributes){
        Constructor constructor = null;
        Object propertyObject=null;
        try {
            Type innerTypeData = modelService.getModel(Property_.clazz.getName(), propertyClass.getName(), Type.class);
            Class innerTypeClass =  Class.forName(innerTypeData.getClazz());

            //Inizialize object
            propertyObject = innerTypeClass.newInstance();
            String [] dataAttrs = data.split(",");
            Map<String,Object> attributeMap= new HashMap<String, Object>();

            for (int k=0;k< attributes.length; k++){
                Class propertyAttributeClass = PropertyUtils.getPropertyType(propertyObject, attributes[k]);
                Constructor propertyAttributeConstructor = propertyAttributeClass.getConstructor(String.class);
                Object propertyAttributeObject = propertyAttributeConstructor.newInstance(dataAttrs[k]);
                attributeMap.put( attributes[k],propertyAttributeObject);
            }

            propertyObject= modelService.getModel(attributeMap, innerTypeClass);

        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return propertyObject;

    }
}
