package org.ddelizia.vcrud.model.cms;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
public class WidgetSlot {

    private String code;

    private Collection<Widget> widgets;
}
