package org.ddelizia.vcrud.gui7.component.search;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import org.ddelizia.vcrud.core.filter.FilterObject;
import org.ddelizia.vcrud.core.filter.FilterType;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.model.VcrudModel;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 7/7/13
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class FilterQuery<T extends VcrudModel> implements org.vaadin.addons.lazyquerycontainer.Query{


    private ModelService modelService;
    private Class<T> theClass;
    private QueryDefinition queryDefinition;
    private String criteria ="";
    private List<FilterObject> filters;

    public FilterQuery(Class<T> theClass, QueryDefinition queryDefinition, List<FilterObject> filters) {
        super();
        modelService = SpringContextHelper.getBean(ModelService.class);
        this.queryDefinition=queryDefinition;
        this.theClass=theClass;
        this.filters=filters;

        for(int i=0;i<queryDefinition.getSortPropertyIds().length;i++) {
            if(i==0) {
                criteria =" ORDER BY";
            } else {
                criteria +=",";
            }
            criteria +=" m."+queryDefinition.getSortPropertyIds()[i];
            if(queryDefinition.getSortPropertyAscendingStates()[i]) {
                criteria +=" ASC";
            }
            else {
                criteria +=" DESC";
            }
        }
    }

    @Override
    public int size() {
        List list = modelService.executeQueryObject(
                buildParameters(),
                buildQuery(true),
                theClass,
                null,
                null);
        return new Integer(list.get(0).toString());
    }

    @Override
    public List<Item> loadItems(int i, int i2) {
        List<T> list = modelService.executeQuery(
                buildParameters(),
                buildQuery(false),
                theClass,
                i,
                i2);

        List<Item> items=new ArrayList<Item>();
        for(T t : list) {
            items.add(new BeanItem<T>(t));
        }
        return items;
    }

    private String buildQuery(boolean isCount){
        String query="";
        if (isCount){
            query =  "SELECT count(m) from "+theClass.getName()+" as m "+criteria;
        }else{
            query =  "SELECT m from "+theClass.getName()+" as m "+criteria;
        }
        for (FilterObject filterObject: filters){
            String filter = filterObject.getSearchType().getSqlOperator();
            for (int i=0; i<filterObject.getSearchType().getNumberOfInputs(); i++){
                filter.replace(FilterType.INPUT_LABEL+i,":"+filterObject.getField()+i);
            }
            query+= " "+ filterObject.getField() + " " +filter+",";
        }
        return query;
    }

    private Map<String,Object> buildParameters(){
        Map<String,Object> parameters = new HashMap<String, Object>();
        for (FilterObject filterObject: filters){
            for (int i=0; i<filterObject.getSearchType().getNumberOfInputs(); i++){
                parameters.put(filterObject.getField()+i, filterObject.getInput()[i]);
            }
        }
        return parameters;
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
