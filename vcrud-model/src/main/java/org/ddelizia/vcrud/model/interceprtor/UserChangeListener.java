package org.ddelizia.vcrud.model.interceprtor;

import org.ddelizia.vcrud.model.usermanagement.User;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 16/09/13
 * Time: 10:29
 * To change this template use File | Settings | File Templates.
 */
public class UserChangeListener implements PropertyChangeListener<User> {

    @Override
    public <E> void onChange(E oldVal, E newVal, String propertyName) {
        System.out.println("property:" + propertyName);
        System.out.println("old  val:" + oldVal);
        System.out.println("new  val:" + newVal);
    }
}
