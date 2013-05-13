package org.ddelizia.vcrud.core.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Property extends VcrudModel {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "clazz")
    private String clazz;

    @Column(name = "simpleName")
    private String simpleName;

    @Column(name = "columnName")
    private String columnName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "type_ref")
    private Type type;


    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
