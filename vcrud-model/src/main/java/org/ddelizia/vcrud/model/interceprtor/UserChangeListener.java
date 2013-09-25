package org.ddelizia.vcrud.model.interceprtor;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.model.usermanagement.User;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 16/09/13
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class UserChangeListener implements PropertyChangeListener<User> {

    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(UserChangeListener.class);

    @Override
    public void onChange(User entity, Object[] oldValue, Object[] newValue, String[] propertyNames, Object[] changedOldValue, Object[] changedNewValue, String[] changedPropertyNames) {
        LOGGER.info("IM entering here");
    }
}
