package org.ddelizia.vcrud.elasticsearch.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 27/10/13
 * Time: 00:53
 * To change this template use File | Settings | File Templates.
 */

public abstract class VcrudElasticSearchEntity {

    @Id
    private String id;

    @Version
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}
