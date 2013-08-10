package org.ddelizia.vcrud.core.service.user;


import org.ddelizia.vcrud.model.usermanagement.Domain;
import org.ddelizia.vcrud.model.usermanagement.User;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 25/06/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public interface UserService {

    public User vcrudLogIn(String username, String password, Domain domain);

    public User registerUser(String username, String email, String password, String password2, Domain domain);

    public User getCurrentUser(HttpServletRequest request);

    public User getCurrentUser(Principal principal);

}
