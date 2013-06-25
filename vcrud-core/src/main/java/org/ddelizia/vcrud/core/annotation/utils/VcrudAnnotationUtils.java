package org.ddelizia.vcrud.core.annotation.utils;

import org.ddelizia.vcrud.core.annotation.VcrudProperty;
import org.ddelizia.vcrud.core.utils.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class VcrudAnnotationUtils {

    public static Field[] retriveVcrudPropertyWithShowOnCombo(Class clazz, boolean result){
        ArrayList<Field> returnValue = new ArrayList<Field>();
        Field [] annotatedFields = ReflectionUtils.getAnnotatedDeclaredFields(clazz, VcrudProperty.class, false);
        for (Field annotatedField: annotatedFields){
            VcrudProperty vcrudProperty = annotatedField.getAnnotation(VcrudProperty.class);
            if (vcrudProperty.showOnCombo()==result){
                returnValue.add(annotatedField);
            }
        }
        return returnValue.toArray(new Field[0]);
    }

    public static Field[] retriveVcrudPropertyWithShowOnResultTable(Class clazz, boolean result){
        ArrayList<Field> returnValue = new ArrayList<Field>();
        Field [] annotatedFields = ReflectionUtils.getAnnotatedDeclaredFields(clazz, VcrudProperty.class, false);
        for (Field annotatedField: annotatedFields){
            VcrudProperty vcrudProperty = annotatedField.getAnnotation(VcrudProperty.class);
            if (vcrudProperty.showOnResultTable()==result){
                returnValue.add(annotatedField);
            }
        }
        return returnValue.toArray(new Field[0]);
    }

}
