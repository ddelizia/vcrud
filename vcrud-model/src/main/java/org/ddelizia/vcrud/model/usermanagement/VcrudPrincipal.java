package org.ddelizia.vcrud.model.usermanagement;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */

public abstract class VcrudPrincipal extends VcrudItem{

    @Indexed(unique = true)
    private String code;

}
