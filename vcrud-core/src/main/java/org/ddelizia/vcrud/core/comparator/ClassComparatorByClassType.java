package org.ddelizia.vcrud.core.comparator;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 17/01/14
 * Time: 18:23
 * To change this template use File | Settings | File Templates.
 */
public class ClassComparatorByClassType implements Comparator<Class> {


    @Override
    public int compare(Class o1, Class o2) {
        if(o1.equals(o2)){
            return 0;
        }else if(o1.isAssignableFrom(o2)){
            return -1;
        }else {
            return 1;
        }
    }

}
