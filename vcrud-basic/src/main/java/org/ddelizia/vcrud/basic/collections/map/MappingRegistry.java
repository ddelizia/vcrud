package org.ddelizia.vcrud.basic.collections.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 17/01/14
 * Time: 18:44
 * To change this template use File | Settings | File Templates.
 */
public interface MappingRegistry<K, V> {

    public void addMapping(K key, V value);

}
