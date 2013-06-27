package org.ddelizia.vcrud.gui7.component;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.core.utils.VcrudAnnotationUtils;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.gui7.frames.scaffolding.SearchPanel;

import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class SearchField extends HorizontalLayout{

    private ModelService modelService;

    private Field field;
    private SearchPanel searchPanel;

    private Label label;
    private ComboBox searchType;
    private Component searchValue;

    public SearchField(Field field){
        this.field=field;
        modelService = SpringContextHelper.getBean(ModelService.class);
        addComponent(getLabel());
        addComponent(getSearchType());
        addComponent(getSearchValue());
    }

    public Label getLabel() {
        if (label==null){
            label = new Label();
        }
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public ComboBox getSearchType() {
        if (searchType==null){
            buildSearchType();
        }
        return searchType;
    }

    public void setSearchType(ComboBox searchType) {
        this.searchType = searchType;
    }

    public Component getSearchValue() {
        if (searchValue==null){
            buildSearchValue();
        }
        return searchValue;
    }

    public void setSearchValue(Component searchValue) {
        this.searchValue = searchValue;
    }

    private void buildSearchValue(){
        if (modelService.isVcrudEntity(field.getType())){
            ComboBox comboBox = new ComboBox();
            JPAContainer jpaContainer = JPAContainerFactory.makeReadOnly(field.getType(), "book-examples");
            comboBox.setContainerDataSource(jpaContainer);

            Field[] fieldstoShow = VcrudAnnotationUtils.retriveVcrudPropertyWithShowOnCombo(field.getType(),true);

            //TODO implement fields to show as a caption

            comboBox.setTextInputAllowed(true);
            comboBox.setFilteringMode(FilteringMode.CONTAINS);

            searchValue = comboBox;
        }else {
            TextField textField = new TextField();
            searchValue = textField;
        }
    }

    private void buildSearchType(){
        searchType = new ComboBox();
        if (modelService.isVcrudEntity(field.getType())){

        }else {

        }
    }
}
