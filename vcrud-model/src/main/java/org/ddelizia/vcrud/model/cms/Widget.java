package org.ddelizia.vcrud.model.cms;

import org.ddelizia.vcrud.model.VcrudModel;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 8:01 PM
 * To change this template use File | Settings | File Templates.
 */

@MappedSuperclass
public abstract class Widget extends VcrudModel{

    private String code;

}
