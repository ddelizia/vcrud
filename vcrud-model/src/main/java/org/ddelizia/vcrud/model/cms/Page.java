package org.ddelizia.vcrud.model.cms;

import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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

    private String code;

    private Template template;

}
