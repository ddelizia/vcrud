package org.ddelizia.vcrud.cms.model.widget;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/17/13
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "Widgets", label = "WidgetLink", parent = "CMS")
public class WidgetLink extends Widget{

    @Column(name = "text")
    private String text;

    @Column(name = "url")
    private String url;

    @Column(name = "cssClass")
    private String cssClass;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCssClass() {
        return cssClass;
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }
}
