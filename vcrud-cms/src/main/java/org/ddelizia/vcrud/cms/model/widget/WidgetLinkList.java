package org.ddelizia.vcrud.cms.model.widget;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/17/13
 * Time: 1:41 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "Widgets", label = "WidgetLinkList", parent = "CMS")
public class WidgetLinkList extends Widget{

    @OneToMany(fetch = FetchType.EAGER)
    private List<WidgetLink> links;

    @Column(name = "cssClass")
    private String cssClass;

    public List<WidgetLink> getLinks() {
        return links;
    }

    public void setLinks(List<WidgetLink> links) {
        this.links = links;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
