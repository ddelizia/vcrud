package org.ddelizia.vcrud.model.basic;


import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Document(collection = "VcrudItem")
public abstract class VcrudItem {

    @Id
    private String id;

    @Version
    private Long version;

    @CreatedDate
    private DateTime creation;

    @LastModifiedDate
    private DateTime lastModification;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DateTime getCreation() {
        return creation;
    }

    public void setCreation(DateTime creation) {
        this.creation = creation;
    }

    public DateTime getLastModification() {
        return lastModification;
    }

    public void setLastModification(DateTime lastModification) {
        this.lastModification = lastModification;
    }
}
