package org.ddelizia.vcrud.model.interceprtor;

import org.ddelizia.vcrud.model.usermanagement.User;
import org.hibernate.EmptyInterceptor;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.HashMap;
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
                    listener.onChange(previousState[i], currentState[i], propertyNames[i]);
                    report = false;
                }
            }
        }
        return false;
    }


    public PropertyChangeInterceptor() {
        listeners = new HashMap<Class, PropertyChangeListener<?>>();
        listeners.put(User.class, new UserChangeListener());
    }
}
