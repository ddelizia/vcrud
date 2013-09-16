package org.ddelizia.vcrud.model.media;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */


@MappedSuperclass
public abstract class Preset extends VcrudModel{

    @Column(name = "code", unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
