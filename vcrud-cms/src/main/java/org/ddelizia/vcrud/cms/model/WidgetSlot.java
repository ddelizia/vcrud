package org.ddelizia.vcrud.cms.model;

import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;
import org.ddelizia.vcrud.cms.model.widget.Widget;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 8:02 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "CMS", label = "WidgetSlot", parent = "CMS")
public class WidgetSlot extends VcrudModel{

    @Column(name = "code", unique = true)
    private String code;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Widget> widgets;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Collection<Widget> getWidgets() {
        return widgets;
    }

    public void setWidgets(Collection<Widget> widgets) {
        this.widgets = widgets;
    }
}
