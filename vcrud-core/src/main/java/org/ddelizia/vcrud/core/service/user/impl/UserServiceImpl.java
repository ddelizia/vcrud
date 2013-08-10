package org.ddelizia.vcrud.core.service.user.impl;

import org.apache.commons.lang.StringUtils;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.user.UserService;
import org.ddelizia.vcrud.model.usermanagement.Domain;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 25/06/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelService modelService;

    @Value("#{domain.enabled}")
    private Boolean domainEnabled;

    @Override
    public User vcrudLogIn(String username, String password, Domain domain) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(User_.username.getName(),username);
        map.put(User_.password.getName(),password);

        if(domainEnabled!=null && domainEnabled.equals(true)){
            map.put(User_.domain.getName(),domain);
        }

        return modelService.getModel(map,User.class);
    }

    @Override
    @Transactional
    public User registerUser(String username, String email, String password, String password2, Domain domain) {
        if (StringUtils.isNotEmpty(password) && StringUtils.equals(password,password2)){
            User u= new User();
            u.setUsername(username);
            u.setPassword(password);
            u.setDomain(domain);
            u.setEmail(email);
            modelService.persist(u);
            return u;
        }
        return null;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return modelService.getModel(User_.username.getName(), request.getUserPrincipal().getName(),User.class);
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return modelService.getModel(User_.username.getName(), principal.getName(),User.class);
    }
}
