package org.ddelizia.vcrud.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public class ReflectionUtils {

    /**
     * Retrieving fields list of specified class and which
     * are annotated by incoming annotation class
     * If recursively is true, retrieving fields from all class hierarchy
     *
     * @param clazz - where fields are searching
     * @param annotationClass - specified annotation class
     * @param recursively param
     * @return list of annotated fields
     */
    public static Field[] getAnnotatedDeclaredFields(Class clazz,
                                                     Class<? extends Annotation> annotationClass,
                                                     boolean recursively) {
        Field[] allFields = getDeclaredFields(clazz, recursively);
        List<Field> annotatedFields = new LinkedList<Field>();

        for (Field field : allFields) {
            if(field.isAnnotationPresent(annotationClass)){
                annotatedFields.add(field);
            }
        }

        return annotatedFields.toArray(new Field[annotatedFields.size()]);
    }

    /**
     * Retrieving fields list of specified class
     * If recursively is true, retrieving fields from all class hierarchy
     *
     * @param clazz where fields are searching
     * @param recursively param
     * @return list of fields
     */
    public static Field[] getDeclaredFields(Class clazz, boolean recursively) {
        List<Field> fields = new LinkedList<Field>();
        Field[] declaredFields = clazz.getDeclaredFields();
        Collections.addAll(fields, declaredFields);

        Class superClass = clazz.getSuperclass();

        if(superClass != null && recursively) {
            Field[] declaredFieldsOfSuper = getDeclaredFields(superClass, recursively);
            if(declaredFieldsOfSuper.length > 0)
                Collections.addAll(fields, declaredFieldsOfSuper);
        }

        return fields.toArray(new Field[fields.size()]);
    }

}
