package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import org.ddelizia.vcrud.core.utils.VcrudAnnotationUtils;
import org.ddelizia.vcrud.gui7.component.SearchField;
import org.ddelizia.vcrud.model.VcrudModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 12:47
 * To change this template use File | Settings | File Templates.
 */
public class SearchPanel extends AbsoluteLayout{

    public static final int BUTTON_LAYOUT=150;

    private List<Field> additionalFieldsList=new ArrayList<Field>();

    private VerticalLayout fieldsLayout;
    private List<SearchField> searchFields = new ArrayList<SearchField>();

    private Class<? extends VcrudModel> clazz;

    private Button filterButton;
    private Button clearButton;
    private ComboBox additionalFields;

    public SearchPanel(Class<? extends VcrudModel> clazz){
        super();
        setClazz(clazz);
        Panel panel = new Panel("Search Box");
        fieldsLayout = new VerticalLayout();
        fieldsLayout.setSizeUndefined();
        fieldsLayout.setMargin(true);
        fieldsLayout.setSpacing(true);
        panel.setHeight(95, Unit.PERCENTAGE);
        panel.setContent(fieldsLayout);

        additionalFields = new ComboBox("Additional Fields");
        additionalFields.setWidth(100, Unit.PERCENTAGE);
        additionalFields.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Object o = additionalFields.getValue();
                if (o!=null){
                    Field field = (Field)additionalFields.getValue();
                    SearchField searchField = new SearchField(field);
                    searchFields.add(searchField);
                    fieldsLayout.addComponent(searchField);

                    additionalFieldsList.remove(searchField);
                    additionalFields.removeItem(field);
                }
            }
        });
        filterButton = new Button("Filter");
        filterButton.setWidth(100,Unit.PERCENTAGE);
        clearButton = new Button("Clear");
        clearButton.setWidth(100,Unit.PERCENTAGE);

        buildFields();

        VerticalLayout buttonsLayout = new VerticalLayout();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);
        buttonsLayout.setSizeUndefined();
        buttonsLayout.setWidth(BUTTON_LAYOUT,Unit.PIXELS);
        buttonsLayout.addComponent(additionalFields);
        buttonsLayout.addComponent(filterButton);
        buttonsLayout.addComponent(clearButton);

        setSizeFull();
        //panel.setVisible(false);
        addComponent(panel,"left: 0px; right: "+BUTTON_LAYOUT+"px;");
        addComponent(buttonsLayout,"right: 0px; bottom: 0px;");
    }

    public void buildFields(){
        fieldsLayout.removeAllComponents();

        if (clazz!=null){
            Field[] allFields = clazz.getDeclaredFields();
            for(Field field: allFields){
                additionalFieldsList.add(field);
            }

            Field fields[] = VcrudAnnotationUtils.retriveVcrudPropertyWithShowOnSearch(clazz, true);
            for (Field field: fields){
                SearchField searchField = new SearchField(field);
                searchFields.add(searchField);
                fieldsLayout.addComponent(searchField);
            }

            additionalFieldsList.removeAll(Arrays.asList(fields));
            for(Field field:additionalFieldsList){
                SearchField searchField = new SearchField(field);
                additionalFields.addItem(field);
                additionalFields.setItemCaption(field,field.getName());
            }

        }
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
