package org.ddelizia.vcrud.core.service;


import org.ddelizia.vcrud.model.Domain;
import org.ddelizia.vcrud.model.User;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 25/06/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

     public User vcrudLogIn(String username, String password, Domain domain);

}
