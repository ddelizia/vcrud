package org.ddelizia.vcrud.core.basic.collection.map;

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
