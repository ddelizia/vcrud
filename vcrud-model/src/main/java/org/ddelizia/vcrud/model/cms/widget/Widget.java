package org.ddelizia.vcrud.model.cms.widget;

import org.ddelizia.vcrud.model.VcrudModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class Widget extends VcrudModel{

    @Column(name = "code", unique = true)
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
