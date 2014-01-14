package org.ddelizia.vcrud.model.usermanagement;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class VcrudPrincipalGroup extends VcrudPrincipal{

    @Indexed(unique = true)
    private String groupName;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
