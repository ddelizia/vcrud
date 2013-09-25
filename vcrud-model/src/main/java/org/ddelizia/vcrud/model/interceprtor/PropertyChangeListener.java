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
     * Used to do something on change.
     *
     * @param entity - the entity
     * @param oldValue - the old object
     * @param newValue - the new object
     * @param propertyNames - the list of property names
     * @param changedOldValue - the list of changed properties in the old object
     * @param changedNewValue - the list of changed properties in the new object
     * @param changedPropertyNames - the list of changed property names
     */
    public void onChange(T entity,
                         Object[] oldValue, Object[] newValue, String[] propertyNames,
                         Object[] changedOldValue, Object[] changedNewValue, String[] changedPropertyNames);
}