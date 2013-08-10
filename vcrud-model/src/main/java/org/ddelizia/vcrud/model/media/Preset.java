package org.ddelizia.vcrud.model.media;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 15:45
 * To change this template use File | Settings | File Templates.
 */


@MappedSuperclass
public abstract class Preset {

    @Column(name = "code", unique = true)
    private String code;
}
