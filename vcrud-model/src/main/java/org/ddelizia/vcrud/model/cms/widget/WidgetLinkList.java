package org.ddelizia.vcrud.model.cms.widget;

import org.ddelizia.vcrud.model.annotation.VcrudItem;
import org.ddelizia.vcrud.model.cms.Widget;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

    private List<WidgetLink> links;

    private String cssClass;
}
