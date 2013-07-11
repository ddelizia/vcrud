package org.ddelizia.vcrud.core.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */

public class FilterTypeMapping {

    private Map<Class<?>,FilterType[]> classMap;

    private Map<Package,FilterType[]> packageMap;

    /**
     * This method retrive the search type startig from the class
     * @param clazz
     * @return The Search types in this order of importance \n 1 - Class\n 2 - Subclass\n 3 - Package
     */
    public FilterType[] retriveSearchType (Class<?> clazz){
        FilterType[] result = null;

        if (classMap.containsKey(clazz)){
            return classMap.get(clazz);
        }
        for(Package aPackage: packageMap.keySet()){
            if(aPackage.equals(clazz.getPackage())){
                result = packageMap.get(aPackage) ;
            }
        }
        for(Class<?> aClass: classMap.keySet()){
            if(clazz.isAssignableFrom(aClass)){
                result = classMap.get(aClass) ;
            }
        }
        return retriveDefaultSearchType();

    }

    private FilterType[] retriveDefaultSearchType (){
        return new FilterType[] {
                FilterType.EQUAL_TO,
                FilterType.GRATERT_THAN,
                FilterType.GRATERT_THAN_OR_EQUAL_TO,
                FilterType.LESS_THAN,
                FilterType.LESS_THAN_OR_EQUAL_TO,
                FilterType.NOT_EQUAL
        };
    }

    private FilterTypeMapping() {
        this.classMap = new HashMap<Class<?>, FilterType[]>();
        this.packageMap = new HashMap<Package, FilterType[]>();
    }
}
