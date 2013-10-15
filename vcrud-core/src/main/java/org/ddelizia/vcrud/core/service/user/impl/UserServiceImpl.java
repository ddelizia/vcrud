package org.ddelizia.vcrud.core.service.user.impl;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.ddelizia.vcrud.core.service.mail.MailService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.user.UserService;
import org.ddelizia.vcrud.model.social.SocialUser;
import org.ddelizia.vcrud.model.social.SocialUser_;
import org.ddelizia.vcrud.model.system.Domain;
import org.ddelizia.vcrud.model.usermanagement.Role;
import org.ddelizia.vcrud.model.usermanagement.User;
import org.ddelizia.vcrud.model.usermanagement.User_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 25/06/13
 * Time: 15:31
 * To change this template use File | Settings | File Templates.
 */
@Service("VcrudUserService")
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private ModelService modelService;

    @Autowired
    private MailService mailService;

    @Override
    public User vcrudLogIn(String username, String password, Domain domain) {
        User user = getUserByUsernameOrEmail(username);
        if (user != null && new Md5PasswordEncoder().encodePassword(password,null).equals(user.getPassword())){
            return user;
        }
        else{
            return null;
        }
    }

    @Override
    public User getUserFromSocialUser(String userName, String accessToken) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(SocialUser_.providerUserId.getName(), userName);
        map.put(SocialUser_.accessToken.getName(), accessToken);
        SocialUser socialUser = modelService.getModel(map, SocialUser.class);
        if (socialUser!=null){
            return socialUser.getUser();
        }else {
            return null;
        }
    }

    @Override
    @Transactional
    public User registerUser(String username, String email, String password, String password2, Domain domain) {
        return registerUser(username, email, password, password2, domain, User.class);
    }

    @Override
    @Transactional
    public <T extends User> T registerUser(String username, String email, String password, String password2, Domain domain, Class<T> userClass) {
        if (StringUtils.isNotEmpty(password) && StringUtils.equals(password,password2)){
            T t= null;
            try {
                t = userClass.newInstance();
                PasswordEncoder passwordEncoder =  new Md5PasswordEncoder();
                t.setUsername(username);
                t.setPassword(passwordEncoder.encodePassword(password, null));
                t.setEmail(email);
                t.setAccountNonLocked(false);
                t.setEnabled(false);
                String generatedUUID = UUID.randomUUID().toString();

                t.setUuidConfirm(generatedUUID);
                modelService.persist(t);

                //TODO Send email
                return t;
            } catch (InstantiationException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (IllegalAccessException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        return null;
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return modelService.getModel(User_.username.getName(), request.getUserPrincipal().getName(),User.class);
    }

    @Override
    public User getCurrentSessionUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null){
            return null;
        }else{
            return getUserByUsernameOrEmail(auth.getName());
        }
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return modelService.getModel(User_.username.getName(), principal.getName(),User.class);
    }

    @Override
    public User getUserByUsernameOrEmail(String usernameOrEmail) {
        User user = modelService.getModel(User_.username.getName(), usernameOrEmail, User.class);
        if (user==null){
            user = modelService.getModel(User_.email.getName(), usernameOrEmail, User.class);
        }
        return user;
    }

    @Override
    @Transactional
    public boolean updateUser(User user) {
        modelService.merge(user);
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(String usernameOrEmail, String key, Object value) {
        try {
            User user = getUserByUsernameOrEmail(usernameOrEmail);
            PropertyUtils.setProperty(user, key, value);
            modelService.merge(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(String usernameOrEmail, Map<String, Object> keyValue) {
        try {
            User user = getUserByUsernameOrEmail(usernameOrEmail);
            for (String key: keyValue.keySet()){
                PropertyUtils.setProperty(user, key, keyValue.get(key));
            }
            modelService.merge(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    @Override
    @Transactional
    public boolean updateUser(User vcrudModel, String key, Object value) {
        try {
            PropertyUtils.setProperty(vcrudModel, key, value);
            modelService.merge(vcrudModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateUser(User vcrudModel, Map<String, Object> keyValue) {
        try {
            for (String key: keyValue.keySet()){
                PropertyUtils.setProperty(vcrudModel, key, keyValue.get(key));
            }
            modelService.merge(vcrudModel);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            return false;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean confirmAccount(String usernameOrEmail, String uuid) {
        User user = getUserByUsernameOrEmail(usernameOrEmail);
        if (user != null && user.getUuidConfirm().equals(uuid)){
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setUuidConfirm(null);
            modelService.rapidPersist(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean restorePasswordFor(String usernameOrEmail) {
        User user = getUserByUsernameOrEmail(usernameOrEmail);
        if(user!=null){
            String generatedUUID = UUID.randomUUID().toString();

            user.setUuidConfirm(generatedUUID);
            modelService.rapidPersist(user);
            //TODO sendemail
            return true;
        }else{
            return false;
        }


    }

    @Override
    public boolean confirmRestorePasswordFor(String usernameOrEmail, String newPassword, String uuid) {
        User user = getUserByUsernameOrEmail(usernameOrEmail);
        if(user!=null && user.getUuidConfirm().equals(uuid)){
            PasswordEncoder passwordEncoder =  new Md5PasswordEncoder();
            user.setPassword(passwordEncoder.encodePassword(newPassword, null));
            user.setUuidRestorePass(null);
            modelService.rapidPersist(user);
            return true;
        } else{
            return false;
        }

    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        org.ddelizia.vcrud.model.usermanagement.User vcrudUser = getUserByUsernameOrEmail(s);
        if (vcrudUser == null){
            throw new UsernameNotFoundException("User not found");
        }
        return createUserDetail(vcrudUser);
    }

    private org.springframework.security.core.userdetails.User createUserDetail(org.ddelizia.vcrud.model.usermanagement.User user){
        Role role = user.getRole();
        List<GrantedAuthority> grantedAuthority = new LinkedList<GrantedAuthority>();
        grantedAuthority.add(new SimpleGrantedAuthority(role.getCode()));

        Date date = new Date();
        Boolean accountNonExpired=false;
        if (user.getExpriteDateAccount()==null || user.getExpriteDateAccount().before(date)){
            accountNonExpired=true;
        }
        Boolean credentialsNonExpired=false;
        if (user.getExpriteDateCredentials()==null || user.getExpriteDateCredentials().before(date)){
            accountNonExpired=true;
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                accountNonExpired,
                credentialsNonExpired,
                user.getAccountNonLocked(),
                grantedAuthority);
    }
}
