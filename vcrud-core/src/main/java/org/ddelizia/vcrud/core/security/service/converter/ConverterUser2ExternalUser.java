package org.ddelizia.vcrud.core.security.service.converter;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.commons.ExternalUser;
import org.ddelizia.vcrud.commons.SocialProfile;
import org.ddelizia.vcrud.core.social.model.SocialUserConnection;
import org.ddelizia.vcrud.core.social.repository.SocialUserConnectionRepository;
import org.ddelizia.vcrud.core.usermanagement.model.User;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author ddelizia
 * @since 16/02/14 12:30
 */
public class ConverterUser2ExternalUser implements Converter<User, ExternalUser>{

    private static final Logger LOGGER = Logger.getLogger(ConverterUser2ExternalUser.class);

    @Autowired
    private SocialUserConnectionRepository socialUserConnectionRepository;

    @Autowired
    @Qualifier(UserService.DEFAULT_BEAN_NAME)
    private UserService userService;


    /**
     * Convert the source of type S to target type T.
     *
     * @param source the source object to convert, which must be an instance of S
     * @return the converted object, which must be an instance of T
     * @throws IllegalArgumentException if the source could not be converted to the desired target type
     */
    @Override
    public ExternalUser convert(User source) {
        Assert.notNull(source);
        ExternalUser externalUser = new ExternalUser();
        externalUser.setId(source.getId());
        externalUser.setEmailAddress(source.getEmail());
        externalUser.setFirstName(source.getFirstName());
        externalUser.setLastName(source.getLastName());
        externalUser.isVerified(userService.isVerified(source));
        for(SocialUserConnection socialUser: socialUserConnectionRepository.findByUser(source)) {
            SocialProfile profile = new SocialProfile();
            profile.setDisplayName(socialUser.getDisplayName());
            profile.setImageUrl(socialUser.getImageUrl());
            profile.setProfileUrl(socialUser.getProfileUrl());
            profile.setProvider(socialUser.getProviderId());
            profile.setProviderUserId(socialUser.getProviderUserId());
            if (externalUser.getSocialProfiles() == null){
                externalUser.setSocialProfiles(new ArrayList<SocialProfile>());
            }
            externalUser.getSocialProfiles().add(profile);
        }
        externalUser.setRoles(new HashSet<String>());
        for (UserGroup userGroup: source.getUserGroups()){
            externalUser.getRoles().add(userGroup.getGroupName());
        }

        return externalUser;
    }

    public void setSocialUserConnectionRepository(SocialUserConnectionRepository socialUserConnectionRepository) {
        this.socialUserConnectionRepository = socialUserConnectionRepository;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
}
