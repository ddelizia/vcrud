package org.ddelizia.vcrud.basic.collections.map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 17/01/14
 * Time: 18:45
 * To change this template use File | Settings | File Templates.
 */
public abstract class MappingRegistrar<K,V> {

    private final MappingRegistry<K,V> registry;

    private K key;
    private V value;

    public MappingRegistrar(MappingRegistry<K,V> registry) {
        this.registry = registry;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public void registerMapping() {
        registry.addMapping(key, value);
    }
}
