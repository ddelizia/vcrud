package org.ddelizia.vcrud.core.media.model;

import org.ddelizia.vcrud.core.basic.model.VcrudItem;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 11:37
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Document(collection = "Media")
public class Media extends VcrudItem{

    private String gridFsIdRef;

    public String getGridFsIdRef() {
        return gridFsIdRef;
    }

    public void setGridFsIdRef(String gridFsIdRef) {
        this.gridFsIdRef = gridFsIdRef;
    }
}
