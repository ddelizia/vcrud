package org.ddelizia.vcrud.gui7.component;

import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.ddelizia.vcrud.core.filter.FilterObject;
import org.ddelizia.vcrud.core.filter.FilterType;
import org.ddelizia.vcrud.core.filter.FilterTypeMapping;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.utils.ReflectionUtils;
import org.ddelizia.vcrud.core.utils.VcrudAnnotationUtils;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.gui7.frames.scaffolding.SearchPanel;
import org.ddelizia.vcrud.model.system.VcrudModel;
import org.ddelizia.vcrud.model.system.VcrudModel_;

import java.lang.reflect.Field;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class SearchField extends HorizontalLayout{

    private FilterTypeMapping filterTypeMapping;

    private static final int SIZE_LABEL = 100;
    private static final int SIZE_SEARCHTYPE = 100;
    private static final int SIZE_SEARCHVALUE = 100;

    private ModelService modelService;

    private Field field;
    private SearchPanel searchPanel;

    private Label label;
    private ComboBox searchType;
    private Component[] searchValues;


    public SearchField(Field field){
        this.field=field;
        modelService = SpringContextHelper.getBean(ModelService.class);
        filterTypeMapping = SpringContextHelper.getBean(FilterTypeMapping.class);
        setSpacing(true);
        addComponent(getLabel());
        addComponent(getSearchType());
        for (Component c:getSearchValues()){
            addComponent(c);
        }

    }

    public Label getLabel() {
        if (label==null){
            label = new Label();
            label.setWidth(SIZE_LABEL,Unit.PIXELS);
            label.setValue(field.getName());
        }
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public ComboBox getSearchType() {
        if (searchType==null){
            searchType=new ComboBox();
            searchType.setWidth(SIZE_SEARCHTYPE,Unit.PIXELS);
            buildSearchType();
        }
        return searchType;
    }

    private void buildSearchType(){
        searchType = new ComboBox();

        for (FilterType filterType: filterTypeMapping.retriveSearchType(field.getType())){
            searchType.addItem(filterType);
            searchType.setItemCaption(filterType,filterType.getCode());
        }
    }

    public void setSearchType(ComboBox searchType) {
        this.searchType = searchType;
    }

    public Component[] getSearchValues() {
        if (searchValues ==null){
            searchValues = new Component[FilterType.MAX_PARAMS];
            for (int i=0; i<searchValues.length; i++){
                searchValues[i]=buildSearchValue();
                if (i>0){
                    searchValues[i].setVisible(false);
                }
            }
        }
        return searchValues;
    }

    public void setSearchValues(Component[] searchValues) {
        this.searchValues = searchValues;
    }

    private Component buildSearchValue(){
        Component component;
        if (modelService.isVcrudEntity(field.getType())){
            ComboBox comboBox = new ComboBox();
            comboBox.setWidth(SIZE_SEARCHVALUE,Unit.PIXELS);

            Field[] fieldstoShow = VcrudAnnotationUtils.retriveVcrudPropertyWithShowOnCombo(field.getType(),true);

            //TODO implement fields to show as a caption

            comboBox.setTextInputAllowed(true);
            comboBox.setFilteringMode(FilteringMode.CONTAINS);

            component = comboBox;
        }else if(field.getType().isAssignableFrom(Collection.class)){
            TextField textField = new TextField();
            textField.setWidth(SIZE_SEARCHVALUE,Unit.PIXELS);

            component = textField;
        }else {
            TextField textField = new TextField();
            textField.setWidth(SIZE_SEARCHVALUE,Unit.PIXELS);

            component = textField;
        }

        return component;
    }

    public FilterObject getFilterObject(){
        FilterObject filterObject=null;
        if (searchType.getValue()!=null){
            filterObject=new FilterObject();
            filterObject.setaClass(field.getType());
            filterObject.setSearchType((FilterType)searchType.getValue());
            filterObject.setField(field.getName());
            filterObject.setInput(new Object[filterObject.getSearchType().getNumberOfInputs()]);
            for (int i=0; i<filterObject.getSearchType().getNumberOfInputs(); i++){
                if(field.getType().getPackage().getName().contains("java.lang")){
                    TextField textField = (TextField)searchValues[i];
                    ReflectionUtils.javaLangBuilder(field.getType(),textField.getValue()) ;
                }else {
                    if(searchValues[i] instanceof ComboBox){
                        ComboBox comboBox =  (ComboBox)searchValues[i];
                        filterObject.getInput()[i] = comboBox.getValue();
                    }else{
                        TextField textField = (TextField)searchValues[i];
                        filterObject.getInput()[i] = modelService.getModel(VcrudModel_.id.getName(), new Long(textField.getValue()), field.getType().asSubclass(VcrudModel.class) );
                    }
                }
            }
        }
        return filterObject;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public void setSearchPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
    }
}
