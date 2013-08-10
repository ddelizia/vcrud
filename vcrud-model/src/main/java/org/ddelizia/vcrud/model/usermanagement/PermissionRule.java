package org.ddelizia.vcrud.model.usermanagement;

import org.ddelizia.vcrud.model.language.MultilanguageString;
import org.ddelizia.vcrud.model.VcrudModel;
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
public class PermissionRule extends VcrudModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_ref")
    private Permission permission;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionRules", cascade = CascadeType.ALL)
    private Collection<User> users;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissionRules", cascade = CascadeType.ALL)
    private Collection<UserGroup> userGroups;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="encodedMultiLanguageString",column=@Column(name="multilanguageString"))})
    private MultilanguageString multilanguageString;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="encodedMultiLanguageString",column=@Column(name="multilanguageString2"))})
    private MultilanguageString multilanguageString2;

    public MultilanguageString getMultilanguageString2() {
        return multilanguageString2;
    }

    public void setMultilanguageString2(MultilanguageString multilanguageString2) {
        this.multilanguageString2 = multilanguageString2;
    }

    public MultilanguageString getMultilanguageString() {
        return multilanguageString;
    }

    public void setMultilanguageString(MultilanguageString multilanguageString) {
        this.multilanguageString = multilanguageString;
    }

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
