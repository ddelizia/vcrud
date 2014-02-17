package org.ddelizia.vcrud.core.security.service.converter;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ddelizia
 * @since 11/02/14 13:16
 */
@Component
public class ConverterUser2UserDetails implements Converter<User, UserDetails>{

    private static final Logger LOGGER = Logger.getLogger(ConverterUser2UserDetails.class);

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;

    @Autowired
    private ConverterUserGroup2GrantedAuthority converterUserGroup2GrantedAuthority;


    /**
     * Convert the source of type S to target type T.
     *
     * @param source the source object to convert, which must be an instance of S
     * @return the converted object, which must be an instance of T
     * @throws IllegalArgumentException if the source could not be converted to the desired target type
     */
    @Override
    public UserDetails convert(User source) {
        Assert.notNull(source);
        return new org.springframework.security.core.userdetails.User(
                source.getName(),
                source.getPassword(),
                source.getEnabled(),
                !userService.isAccountExpired(source),
                !userService.isAccountCredentialsExpired(source),
                !userService.isAccountLocked(source),
                createGrantedAuthorityList(source)
        );
    }

    private List<GrantedAuthority> createGrantedAuthorityList(User user){
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserGroup userGroup: user.getUserGroups()){
            grantedAuthorities.add(converterUserGroup2GrantedAuthority.convert(userGroup));
        }
        return grantedAuthorities;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public ConverterUserGroup2GrantedAuthority getConverterUserGroup2GrantedAuthority() {
        return converterUserGroup2GrantedAuthority;
    }

    public void setConverterUserGroup2GrantedAuthority(ConverterUserGroup2GrantedAuthority converterUserGroup2GrantedAuthority) {
        this.converterUserGroup2GrantedAuthority = converterUserGroup2GrantedAuthority;
    }
}
