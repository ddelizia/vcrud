package org.ddelizia.vcrud.model.elasticsearch;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/09/13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ElasticSearchType extends VcrudModel {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elasticSearchType", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElasticSearchField> elasticSearchFields;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "elasticSearchIndex_ref")
    private ElasticSearchIndex elasticSearchIndex;

    public List<ElasticSearchField> getElasticSearchFields() {
        return elasticSearchFields;
    }

    public void setElasticSearchFields(List<ElasticSearchField> elasticSearchFields) {
        this.elasticSearchFields = elasticSearchFields;
    }

    public ElasticSearchIndex getElasticSearchIndex() {
        return elasticSearchIndex;
    }

    public void setElasticSearchIndex(ElasticSearchIndex elasticSearchIndex) {
        this.elasticSearchIndex = elasticSearchIndex;
    }
}
