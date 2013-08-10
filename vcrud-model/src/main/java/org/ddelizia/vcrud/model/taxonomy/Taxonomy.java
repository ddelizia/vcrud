package org.ddelizia.vcrud.model.taxonomy;

import org.ddelizia.vcrud.model.VcrudModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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

    private Collection<TaxonomyItem> taxonomyItems;
}
