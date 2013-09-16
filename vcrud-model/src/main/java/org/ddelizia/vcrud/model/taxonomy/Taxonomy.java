package org.ddelizia.vcrud.model.taxonomy;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/15/13
 * Time: 6:15 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Taxonomy extends VcrudModel {

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "taxonomy", orphanRemoval = true, cascade = CascadeType.ALL)
    private Collection<TaxonomyItem> taxonomyItems;

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

    public Collection<TaxonomyItem> getTaxonomyItems() {
        return taxonomyItems;
    }

    public void setTaxonomyItems(Collection<TaxonomyItem> taxonomyItems) {
        this.taxonomyItems = taxonomyItems;
    }
}
