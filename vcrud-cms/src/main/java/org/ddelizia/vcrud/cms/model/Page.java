package org.ddelizia.vcrud.cms.model;

import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 7:57 PM
 * To change this template use File | Settings | File Templates.
 */


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "CMS", label = "Page", parent = "CMS")
public class Page extends VcrudModel{

    @Column(name = "code", unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_ref")
    private Template template;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<WidgetSlot> widgetSlots;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
