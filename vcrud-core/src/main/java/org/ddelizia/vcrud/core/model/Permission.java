package org.ddelizia.vcrud.core.model;

import org.ddelizia.vcrud.core.model.enumeration.PermissionEnum;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "code", nullable = false, unique = true)
    private PermissionEnum code;

    public PermissionEnum getCode() {
        return code;
    }

    public void setCode(PermissionEnum code) {
        this.code = code;
    }
}
