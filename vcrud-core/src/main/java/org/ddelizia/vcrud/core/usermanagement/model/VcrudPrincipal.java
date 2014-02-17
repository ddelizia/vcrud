package org.ddelizia.vcrud.core.usermanagement.model;

import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.Entity;
import java.security.Principal;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */
@Entity
public abstract class VcrudPrincipal extends VcrudItem implements Principal {

    @Indexed(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
