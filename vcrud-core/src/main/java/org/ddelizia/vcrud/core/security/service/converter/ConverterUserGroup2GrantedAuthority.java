package org.ddelizia.vcrud.core.security.service.converter;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author ddelizia
 * @since 11/02/14 13:19
 */
@Component
public class ConverterUserGroup2GrantedAuthority implements Converter<UserGroup, GrantedAuthority>{

    private static final Logger LOGGER = Logger.getLogger(ConverterUserGroup2GrantedAuthority.class);


    /**
     * Convert the source of type S to target type T.
     *
     * @param source the source object to convert, which must be an instance of S
     * @return the converted object, which must be an instance of T
     * @throws IllegalArgumentException if the source could not be converted to the desired target type
     */
    @Override
    public GrantedAuthority convert(UserGroup source) {
        Assert.notNull(source);
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(source.getGroupName());
        return grantedAuthority;
    }
}
