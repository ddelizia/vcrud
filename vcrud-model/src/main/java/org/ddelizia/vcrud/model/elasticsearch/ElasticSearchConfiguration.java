package org.ddelizia.vcrud.model.elasticsearch;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 24/09/13
 * Time: 13:56
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ElasticSearchConfiguration extends VcrudModel{

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "elasticSearchConfiguration", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElasticSearchIndex> elasticSearchIndexes;

    @Column(name = "url", nullable = false)
    private String url;

    public List<ElasticSearchIndex> getElasticSearchIndexes() {
        return elasticSearchIndexes;
    }

    public void setElasticSearchIndexes(List<ElasticSearchIndex> elasticSearchIndexes) {
        this.elasticSearchIndexes = elasticSearchIndexes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
