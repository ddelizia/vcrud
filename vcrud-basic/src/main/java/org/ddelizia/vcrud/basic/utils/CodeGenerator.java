package org.ddelizia.vcrud.basic.utils;

import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 11/01/14
 * Time: 09:39
 * To change this template use File | Settings | File Templates.
 */
public class CodeGenerator {

    public static String calculateUUIDCode(Object o){
        return UUID.randomUUID()+"_"+o.getClass();
    }
}
