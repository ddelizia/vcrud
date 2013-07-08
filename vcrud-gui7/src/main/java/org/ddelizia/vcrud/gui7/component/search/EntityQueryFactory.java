package org.ddelizia.vcrud.gui7.component.search;

import org.ddelizia.vcrud.model.VcrudModel;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 9:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityQueryFactory<T extends VcrudModel> implements QueryFactory{

    private QueryDefinition definition;
    private Class<T> tClass;

    public EntityQueryFactory(Class<T> tClass) {
        super();
        this.tClass=tClass;
    }


    @Override
    public Query constructQuery(QueryDefinition queryDefinition) {
        return new EntityQuery<T>(tClass);
    }

}
