package org.ddelizia.vcrud.core.usermanagement.service.impl;

import org.ddelizia.vcrud.core.config.AppConfig;
import org.ddelizia.vcrud.core.exception.ModelExistsException;
import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;
import org.ddelizia.vcrud.core.usermanagement.repository.UserGroupRepository;
import org.ddelizia.vcrud.core.usermanagement.service.UserGroupService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/02/14
 * Time: 17:39
 * To change this template use File | Settings | File Templates.
 */
@Service(UserGroupService.DEFAULT_BEAN_NAME)
public class UserGroupServiceImpl implements UserGroupService{

    @Resource
    private UserGroupRepository userGroupRepository;

    @Resource
    private AppConfig appConfig;

    @Override
    public UserGroup getUserGroupFromProperty(String property) {
        Assert.notNull(property);

        String restGroupName = appConfig.getProperty(property, String.class, null);
        Assert.notNull(restGroupName);

        UserGroup userGroup = userGroupRepository.findByGroupName(restGroupName);
        Assert.notNull(userGroup);

        return userGroup;
    }

    @Override
    public UserGroup addUserGroupAsChildOf(String userGroupName, UserGroup father) {
        Assert.notNull(userGroupName);
        Assert.notNull(father);

        if (userGroupRepository.findByGroupName(userGroupName)!=null){
            throw new ModelExistsException(UserGroup.class, userGroupName + "already Exists");
        }

        UserGroup group = new UserGroup();
        group.setFather(father);
	    if (father.getFather()!=null){
		    group.getAnchestors().add(father.getFather());
		    group.getAnchestors().addAll(father.getAnchestors());
	    }
        userGroupRepository.save(group);

        return group;
    }

    @Override
    public UserGroup addUserGroupAsRoot(String userGroup) {
        Assert.notNull(userGroup);
        if (userGroupRepository.findByGroupName(userGroup)!=null){
            throw new ModelExistsException(UserGroup.class, userGroup + "already Exists");
        }
        UserGroup group = new UserGroup();
        group.setFather(null);
        userGroupRepository.save(group);
        return group;
    }

	@Override
	public UserGroup getAuthenticatedGroup() {
		return getUserGroupFromProperty(AppConfig.USER_USERGROUP_AUTHENTICATED);
	}
}

