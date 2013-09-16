package org.ddelizia.vcrud.model.interceprtor;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 16/09/13
 * Time: 10:27
 * To change this template use File | Settings | File Templates.
 */
public interface PropertyChangeListener <T> {

    /**
     * Used to do something on change. Note that the type of values is inferred by <E>
     *
     * @param propertyName - the property name on the entity
     * @param oldVal - the old value
     * @param newVal - the new value
     */
    <E> void onChange(E oldVal, E newVal, String propertyName);
}