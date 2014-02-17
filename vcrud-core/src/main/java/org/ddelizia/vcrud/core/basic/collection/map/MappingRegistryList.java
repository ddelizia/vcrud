package org.ddelizia.vcrud.core.basic.collection.map;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 18/01/14
 * Time: 10:14
 * To change this template use File | Settings | File Templates.
 */
public class MappingRegistryList<K,V> implements MappingRegistry<K,V> {

    private final Map<K, List<V>> mappings = new HashMap<K, List<V>>();

    public void addMapping(K key, V value) {
        if (mappings.get(key)==null){
            mappings.put(key, Arrays.asList(value));
        }else {
            mappings.get(key).add(value);
        }
    }

    public Map<K, List<V>> getMappings() {
        return Collections.unmodifiableMap(mappings);
    }

}
