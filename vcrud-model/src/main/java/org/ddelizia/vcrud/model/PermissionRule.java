package org.ddelizia.vcrud.model;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/06/13
 * Time: 18:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "User Management", parent = VcrudItem.ROOT, label = "PermissionRules")
public class PermissionRule extends VcrudModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_ref")
    private Permission permission;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionRules", cascade = CascadeType.ALL)
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionRules", cascade = CascadeType.ALL)
    private Collection<UserGroup> userGroups;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Collection<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }
}
