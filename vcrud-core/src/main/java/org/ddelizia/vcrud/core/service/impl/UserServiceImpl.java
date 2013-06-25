package org.ddelizia.vcrud.core.service.impl;

import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.core.service.UserService;
import org.ddelizia.vcrud.model.Domain;
import org.ddelizia.vcrud.model.User;
import org.ddelizia.vcrud.model.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

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
}
