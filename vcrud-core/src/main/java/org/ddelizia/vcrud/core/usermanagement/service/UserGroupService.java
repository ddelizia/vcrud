package org.ddelizia.vcrud.core.usermanagement.service;

import org.ddelizia.vcrud.core.usermanagement.model.UserGroup;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/02/14
 * Time: 17:37
 * To change this template use File | Settings | File Templates.
 */
public interface UserGroupService {

    public static final String DEFAULT_BEAN_NAME = "defaultUserGroupService";

    public UserGroup getUserGroupFromProperty (String property);

    public UserGroup addUserGroupAsChildOf(String userGroupName, UserGroup father);

    public UserGroup addUserGroupAsRoot(String userGroup);

	public UserGroup getAuthenticatedGroup();
}
