package org.ddelizia.vcrud.elasticsearch.repository.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/10/13
 * Time: 20:01
 * To change this template use File | Settings | File Templates.
 */
public interface VcrudElasticsearchRepository <T extends VcrudElasticsearchRepository, ID extends Serializable> extends ElasticsearchRepository<T, ID>, PagingAndSortingRepository<T, ID> {
}
