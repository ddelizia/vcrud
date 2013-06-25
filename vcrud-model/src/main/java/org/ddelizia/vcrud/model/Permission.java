package org.ddelizia.vcrud.model;

import org.ddelizia.vcrud.core.model.enumeration.PermissionEnum;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:47
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Permission extends VcrudModel {

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false, unique = true)
    private PermissionEnum permission;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Collection<PermissionRule> permissionRules;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PermissionEnum getPermission() {
        return permission;
    }

    public void setPermission(PermissionEnum permission) {
        this.permission = permission;
    }

    public Collection<PermissionRule> getPermissionRules() {
        return permissionRules;
    }

    public void setPermissionRules(Collection<PermissionRule> permissionRules) {
        this.permissionRules = permissionRules;
    }
}
