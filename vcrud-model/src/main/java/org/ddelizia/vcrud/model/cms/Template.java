package org.ddelizia.vcrud.model.cms;

import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

    private String code;

    private Collection<WidgetSlot> widgetSlots;
}
