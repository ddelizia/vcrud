package org.ddelizia.vcrud.gui7.component.search;

import org.ddelizia.vcrud.model.VcrudModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class SertchTypeMapping {

    private Map<Class<?>,SearchType[]> classMap;

    private Map<Package,SearchType[]> packageMap;

    /**
     * This method retrive the search type startig from the class
     * @param clazz
     * @return The Search types in this order of importance \n 1 - Class\n 2 - Subclass\n 3 - Package
     */
    public SearchType[] retriveSearchType (Class<?> clazz){
        SearchType[] result = null;

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
        return result;

    }

    private SertchTypeMapping() {
        this.classMap = new HashMap<Class<?>, SearchType[]>();
        this.packageMap = new HashMap<Package, SearchType[]>();

        classMap.put(String.class, new SearchType [] {SearchType.EQUALS,SearchType.CONTAINS, SearchType.STARTS_WITH, SearchType.ENDS_WITH});
        classMap.put(Number.class, new SearchType [] {SearchType.EQUALS,SearchType.LESSER, SearchType.GREATER});
        classMap.put(Date.class, new SearchType [] {SearchType.EQUALS,SearchType.LESSER, SearchType.GREATER});
    }
}
