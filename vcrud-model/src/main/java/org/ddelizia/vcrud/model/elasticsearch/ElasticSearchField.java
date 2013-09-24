package org.ddelizia.vcrud.model.elasticsearch;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/09/13
 * Time: 14:49
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ElasticSearchField extends VcrudModel{

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "elasticSearchType_ref")
    private ElasticSearchType elasticSearchType;

    public ElasticSearchType getElasticSearchType() {
        return elasticSearchType;
    }

    public void setElasticSearchType(ElasticSearchType elasticSearchType) {
        this.elasticSearchType = elasticSearchType;
    }
}
