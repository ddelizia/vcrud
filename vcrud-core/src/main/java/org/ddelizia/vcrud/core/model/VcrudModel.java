package org.ddelizia.vcrud.core.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

@MappedSuperclass
public abstract class VcrudModel {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "lastModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    @PrePersist
    private void privatePrePersist(){
        setLastModification(currentDate());
        prePersist();
    }

    @PreUpdate
    private void privatePreUpdate(){
        setLastModification(currentDate());
        preUpdate();
    }

    @PreRemove
    private void privatePreRemove(){
        preRemove();
    }

    /**
     * Implement this method if you want to add prePersist Functionality
     */
    public void prePersist(){

    }

    /**
     * Implement this method if you want to add preUpdate Functionality
     */
    public void preUpdate(){

    }

    /**
     * Implement this method if you want to add preRemove Functionality
     */
    public void preRemove(){

    }

    private Date currentDate(){
        return new Date();
    }


}
