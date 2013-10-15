package org.ddelizia.vcrud.core.factory.modelproperty;

import org.ddelizia.vcrud.core.config.ApplicationContextProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/29/13
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */


public class ModelPropertyResolver {

    private Map<Class<?>, ModelPropertyBuilder> mapClass = new HashMap<Class<?>, ModelPropertyBuilder>();
    private Map<String, ModelPropertyBuilder> mapPackage = new HashMap<String, ModelPropertyBuilder>();

    public Object modelPropertyBuilder(Class<?> propertyClass, String data, String[] attributes){
        ModelPropertyBuilder modelPropertyBuilder = retrieveModelPropertyBuilder(propertyClass,propertyClass.getPackage().getName().toString());
        return modelPropertyBuilder.build(propertyClass, data, attributes);
    }

    public ModelPropertyBuilder retrieveModelPropertyBuilder(Class<?> clazz, String packageName){

        ModelPropertyBuilder modelPropertyBuilder = null;
        if (clazz!=null){
            modelPropertyBuilder = findByClass(clazz);
        }
        if (packageName!=null && modelPropertyBuilder == null){
            modelPropertyBuilder = findByPackage(packageName);
        }
        return modelPropertyBuilder;

    }


    private ModelPropertyBuilder findByClass(Class<?> aClass){
        ModelPropertyBuilder modelPropertyBuilder = mapClass.get(aClass);
        if (modelPropertyBuilder == null){
            for (Class<?> currentClass: mapClass.keySet() ){
                if(currentClass.isAssignableFrom(aClass)){
                    return mapClass.get(currentClass);
                }
            }
        }
        return modelPropertyBuilder;
    }

    private ModelPropertyBuilder findByPackage(String aPackage){
        ModelPropertyBuilder modelPropertyBuilder = mapPackage.get(aPackage);
        return modelPropertyBuilder;
    }

    public Map<Class<?>, ModelPropertyBuilder> getMapClass() {
        return mapClass;
    }

    public void setMapClass(Map<Class<?>, ModelPropertyBuilder> mapClass) {
        this.mapClass = mapClass;
    }

    public Map<String, ModelPropertyBuilder> getMapPackage() {
        return mapPackage;
    }

    public void setMapPackage(Map<String, ModelPropertyBuilder> mapPackage) {
        this.mapPackage = mapPackage;
    }
}
