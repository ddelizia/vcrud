package org.ddelizia.vcrud.model.taxonomy;

import org.ddelizia.vcrud.model.VcrudModel;
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

    private Collection<Image> taxonomyImages;

    private Taxonomy taxonomy;

}
