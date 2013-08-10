package org.ddelizia.vcrud.model;

import org.ddelizia.vcrud.model.usermanagement.Domain;

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

    @Column(name = "creation")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation;

    @Column(name = "lastModification")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModification;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "domain_ref")
    private Domain domain;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
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
