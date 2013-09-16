package org.ddelizia.vcrud.model.system;

import org.hibernate.annotations.GenericGenerator;

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
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    @Column(name = "lastModification")
    @Temporal(TemporalType.TIMESTAMP)
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

    @PrePersist
    private void privatePrePersist(){
        setCreation(currentDate());
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

    private boolean vcrudEqual(VcrudModel vcrudModel){
        return vcrudModel.getId().equals(this.getId());
    }

}
