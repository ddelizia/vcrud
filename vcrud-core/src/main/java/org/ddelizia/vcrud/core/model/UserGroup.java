package org.ddelizia.vcrud.core.model;

import org.ddelizia.vcrud.core.annotation.VcrudItem;

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
    Collection<User> users;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name="usergroup2permissionrule",
            joinColumns={@JoinColumn(name="usergroup_id", referencedColumnName="id")},
            inverseJoinColumns={@JoinColumn(name="permissionrule_id", referencedColumnName="id")})
    Collection<PermissionRule> permissionRules;
}
