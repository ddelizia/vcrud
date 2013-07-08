package org.ddelizia.vcrud.gui7.component.search;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.model.VcrudModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityQuery<T extends VcrudModel> implements org.vaadin.addons.lazyquerycontainer.Query{


    private ModelService modelService;
    private Class<T> theClass;

    public EntityQuery(Class<T> theClass) {
        super();
        modelService = SpringContextHelper.getBean(ModelService.class);
        this.theClass=theClass;
    }

    @Override
    public int size() {
        return modelService.count(theClass);
    }

    @Override
    public List<Item> loadItems(int i, int i2) {
        List<T> list = modelService.getModels(this.theClass,i,i2);
        List<Item> items=new ArrayList<Item>();
        for(T t : list) {
            items.add(new BeanItem<T>(t));
        }
        return items;
    }

    @Override
    public void saveItems(List<Item> items, List<Item> items2, List<Item> items3) {
        throw new UnsupportedOperationException("This is not supported");
    }

    @Override
    public boolean deleteAllItems() {
        throw new UnsupportedOperationException("This is not supported");
    }

    @Override
    public Item constructItem() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}