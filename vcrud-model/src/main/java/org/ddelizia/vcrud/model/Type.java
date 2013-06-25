package org.ddelizia.vcrud.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:12
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Type extends VcrudModel {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "clazz")
    private String clazz;

    @Column(name = "simpleClazzName")
    private String simpleClazzName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "type", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Property> property;

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getSimpleClazzName() {
        return simpleClazzName;
    }

    public void setSimpleClazzName(String simpleClazzName) {
        this.simpleClazzName = simpleClazzName;
    }

    public Set<Property> getProperty() {
        return property;
    }

    public void setProperty(Set<Property> property) {
        this.property = property;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
