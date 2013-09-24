package org.ddelizia.vcrud.model.elasticsearch;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/09/13
 * Time: 13:57
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ElasticSearchIndex extends VcrudModel{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elasticSearchIndex", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElasticSearchType> elasticSearchTypes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "elasticSearchConfiguration_ref")
    private ElasticSearchConfiguration elasticSearchConfiguration;

    public List<ElasticSearchType> getElasticSearchTypes() {
        return elasticSearchTypes;
    }

    public void setElasticSearchTypes(List<ElasticSearchType> elasticSearchTypes) {
        this.elasticSearchTypes = elasticSearchTypes;
    }

    public ElasticSearchConfiguration getElasticSearchConfiguration() {
        return elasticSearchConfiguration;
    }

    public void setElasticSearchConfiguration(ElasticSearchConfiguration elasticSearchConfiguration) {
        this.elasticSearchConfiguration = elasticSearchConfiguration;
    }
}
