package org.ddelizia.vcrud.core.basic.collection.map;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 12:58
 * To change this template use File | Settings | File Templates.
 */
public class MappingRegistrySimple <K,V> implements MappingRegistry<K,V> {

    private final Map<K, V> mappings = new HashMap<K, V>();

    public void addMapping(K key, V value) {
        mappings.put(key, value);
    }

    public Map<K, V> getMappings() {
        return Collections.unmodifiableMap(mappings);
    }

}
