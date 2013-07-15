package org.ddelizia.vcrud.gui7.component.search;

import org.ddelizia.vcrud.core.filter.FilterObject;
import org.ddelizia.vcrud.model.VcrudModel;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterQueryFactory<T extends VcrudModel> implements QueryFactory{

    private QueryDefinition definition;
    private Class<T> tClass;
    private List<FilterObject> filters;

    public FilterQueryFactory(Class<T> tClass, List<FilterObject> filters) {
        super();
        this.tClass=tClass;
        this.filters=filters;
    }

    @Override
    public Query constructQuery(QueryDefinition queryDefinition) {
        this.definition=queryDefinition;
        return new FilterQuery<T>(tClass,queryDefinition, filters);
    }
}
