package org.ddelizia.vcrud.model.interceprtor;

import org.hibernate.EmptyInterceptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 16/09/13
 * Time: 10:28
 * To change this template use File | Settings | File Templates.
 */
public class PropertyChangeInterceptor  extends EmptyInterceptor {

    private static final long serialVersionUID = 1L;

    private Map<Class, PropertyChangeListener<?>> listeners;

    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState, String[] propertyNames, org.hibernate.type.Type[] types) {
        PropertyChangeListener listener = listeners.get(entity.getClass());

        List<Object> currentStateChanged = new ArrayList<Object>();
        List<Object> previousStateChanged = new ArrayList<Object>();
        List<String> propertyNamesChanged = new ArrayList<String>();

        //Only check for changes if an entity-specific listener was registered
        if (listener != null) {
            boolean report = false;
            for (int i = 0; i < currentState.length; i++) {
                if (currentState[i] == null) {
                    if (previousState[i] != null) {
                        report = true;
                    }
                } else if (!currentState[i].equals(previousState[i])) {
                    report = true;
                }

                if (report) {
                    currentStateChanged.add(currentState[i]);
                    previousStateChanged.add(previousState[i]);
                    propertyNamesChanged.add(propertyNames[i]);
                    report = false;
                }
            }
            listener.onChange(entity, previousState, currentState, propertyNames, previousStateChanged.toArray(), currentStateChanged.toArray(), propertyNamesChanged.toArray(new String[0]));
        }
        return false;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Map<Class, PropertyChangeListener<?>> getListeners() {
        return listeners;
    }

    public void setListeners(Map<Class, PropertyChangeListener<?>> listeners) {
        this.listeners = listeners;
    }

    public PropertyChangeInterceptor() {
        listeners = new HashMap<Class, PropertyChangeListener<?>>();
    }
}
