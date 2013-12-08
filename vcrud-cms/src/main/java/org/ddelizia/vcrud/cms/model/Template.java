package org.ddelizia.vcrud.cms.model;

import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/17/13
 * Time: 1:31 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "CMS", label = "Template", parent = "CMS")
public class Template extends VcrudModel{

    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<WidgetSlot> widgetSlots;
}
