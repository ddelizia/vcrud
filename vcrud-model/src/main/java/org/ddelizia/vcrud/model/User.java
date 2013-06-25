package org.ddelizia.vcrud.model;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "User Management", parent = VcrudItem.ROOT, label = "User")
public class User extends VcrudModel {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_ref")
    private Role role;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="user2usergroup")
    private Collection<UserGroup> userGroups;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name="user2permissionrule")
    private Collection<PermissionRule> permissionRules;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Collection<UserGroup> getUserGroups() {
        return userGroups;
    }

    public void setUserGroups(Collection<UserGroup> userGroups) {
        this.userGroups = userGroups;
    }

    public Collection<PermissionRule> getPermissionRules() {
        return permissionRules;
    }

    public void setPermissionRules(Collection<PermissionRule> permissionRules) {
        this.permissionRules = permissionRules;
    }
}
