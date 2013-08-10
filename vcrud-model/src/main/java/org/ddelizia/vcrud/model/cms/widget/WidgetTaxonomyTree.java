package org.ddelizia.vcrud.model.cms.widget;

import org.ddelizia.vcrud.model.annotation.VcrudItem;
import org.ddelizia.vcrud.model.taxonomy.Taxonomy;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/17/13
 * Time: 1:42 PM
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(group = "Widgets", label = "WidgetTaxonomyTree", parent = "CMS")
public class WidgetTaxonomyTree extends Widget{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "taxonomy_ref")
    private Taxonomy taxonomy;

    @Column(name = "cssUl")
    private String cssUl;

    @Column(name = "cssLi")
    private String cssLi;

    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    public void setTaxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getCssUl() {
        return cssUl;
    }

    public void setCssUl(String cssUl) {
        this.cssUl = cssUl;
    }

    public String getCssLi() {
        return cssLi;
    }

    public void setCssLi(String cssLi) {
        this.cssLi = cssLi;
    }
}
