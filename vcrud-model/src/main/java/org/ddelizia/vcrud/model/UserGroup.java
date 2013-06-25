package org.ddelizia.vcrud.model;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/06/13
 * Time: 17:45
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "User Management", parent = VcrudItem.ROOT, label = "UserGroup")
public class UserGroup extends VcrudModel{

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "userGroups", cascade = CascadeType.ALL)
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="usergroup2permissionrule")
    private Collection<PermissionRule> permissionRules;

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public Collection<PermissionRule> getPermissionRules() {
        return permissionRules;
    }

    public void setPermissionRules(Collection<PermissionRule> permissionRules) {
        this.permissionRules = permissionRules;
    }
}
