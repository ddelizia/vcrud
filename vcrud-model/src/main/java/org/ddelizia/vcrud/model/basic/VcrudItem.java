package org.ddelizia.vcrud.model.basic;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

@Document(collection = "VcrudItem")
public abstract class VcrudItem {

    @Id
    private String id;

    private Date creation;

    private Date lastModification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreation() {
        return creation;
    }

    public void setCreation(Date creation) {
        this.creation = creation;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }
}
