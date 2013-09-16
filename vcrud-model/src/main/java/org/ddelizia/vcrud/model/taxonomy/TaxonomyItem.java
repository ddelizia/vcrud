package org.ddelizia.vcrud.model.taxonomy;

import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.media.Image;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 6:13 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class TaxonomyItem extends VcrudModel {

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<TaxonomyItem> parentTaxonomyItems;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active = false;

    @Column(name = "startActiveDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startActiveDate;

    @Column(name = "endActiveDAte")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endActiveDAte;

    @OneToMany(fetch = FetchType.LAZY)
    private Collection<Image> taxonomyImages;

    @ManyToOne(fetch = FetchType.LAZY)
    private Taxonomy taxonomy;

    public Collection<TaxonomyItem> getParentTaxonomyItems() {
        return parentTaxonomyItems;
    }

    public void setParentTaxonomyItems(Collection<TaxonomyItem> parentTaxonomyItems) {
        this.parentTaxonomyItems = parentTaxonomyItems;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getStartActiveDate() {
        return startActiveDate;
    }

    public void setStartActiveDate(Date startActiveDate) {
        this.startActiveDate = startActiveDate;
    }

    public Date getEndActiveDAte() {
        return endActiveDAte;
    }

    public void setEndActiveDAte(Date endActiveDAte) {
        this.endActiveDAte = endActiveDAte;
    }

    public Collection<Image> getTaxonomyImages() {
        return taxonomyImages;
    }

    public void setTaxonomyImages(Collection<Image> taxonomyImages) {
        this.taxonomyImages = taxonomyImages;
    }

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }
}
