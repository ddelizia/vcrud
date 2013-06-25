package org.ddelizia.vcrud.core.model;

import org.ddelizia.vcrud.core.annotation.VcrudItem;

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

}
