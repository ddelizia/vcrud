package org.ddelizia.vcrud.model.cms.widget;

import org.ddelizia.vcrud.model.annotation.VcrudItem;
import org.ddelizia.vcrud.model.cms.Widget;
import org.ddelizia.vcrud.model.taxonomy.Taxonomy;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

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

    private Taxonomy taxonomy;

    private String cssUl;

    private String cssLi;

}
