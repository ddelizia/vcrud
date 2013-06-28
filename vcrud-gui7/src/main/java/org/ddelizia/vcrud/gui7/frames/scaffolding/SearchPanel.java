package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.vaadin.ui.Button;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.core.utils.VcrudAnnotationUtils;
import org.ddelizia.vcrud.gui7.component.SearchField;
import org.ddelizia.vcrud.model.VcrudModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class SearchPanel extends Panel{

    private VerticalLayout fieldsLayout;
    private List<SearchField> searchFields = new ArrayList<SearchField>();

    private Class<? extends VcrudModel> clazz;

    private Button filter;

    public SearchPanel(Class<? extends VcrudModel> clazz){
        super("Search Box");
        setClazz(clazz);
        fieldsLayout = new VerticalLayout();
        fieldsLayout.setSizeUndefined();
        fieldsLayout.setMargin(true);
        fieldsLayout.setSpacing(true);
        this.setWidth(100, Unit.PERCENTAGE);
        this.setHeight(33, Unit.PERCENTAGE);
        this.setContent(fieldsLayout);
        buildFields();
    }

    public void buildFields(){
        fieldsLayout.removeAllComponents();
        if (clazz!=null){
            Field fields[] = VcrudAnnotationUtils.retriveVcrudPropertyWithShowOnSearch(clazz, true);
            for (Field field: fields){
                SearchField searchField = new SearchField(field);
                searchFields.add(searchField);
                fieldsLayout.addComponent(searchField);
            }
        }
        filter = new Button("Filter");
        fieldsLayout.addComponent(filter);
    }

    public VerticalLayout getFieldsLayout() {
        return fieldsLayout;
    }

    public void setFieldsLayout(VerticalLayout fieldsLayout) {
        this.fieldsLayout = fieldsLayout;
    }

    public List<SearchField> getSearchFields() {
        return searchFields;
    }

    public void setSearchFields(List<SearchField> searchFields) {
        this.searchFields = searchFields;
    }

    public Class<? extends VcrudModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends VcrudModel> clazz) {
        this.clazz = clazz;
    }
}
