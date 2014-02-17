package org.ddelizia.vcrud.core.security.service.impl;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.basic.service.AbstractService;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 11/02/14
 * Time: 12:20
 * To change this template use File | Settings | File Templates.
 */


public class UserDetailsServiceImpl extends AbstractService implements UserDetailsService{

    private static final Logger LOGGER = Logger.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        UserDetails user = new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getPassword() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public String getUsername() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public boolean isEnabled() {
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        return null;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
