package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.vaadin.ui.*;
import org.ddelizia.vcrud.model.User;
import org.ddelizia.vcrud.model.VcrudModel;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class DataCrud extends VerticalLayout{

    private ResultsTable<User> resultsTable;

    private SearchPanel searchPanel;

    private Class<? extends VcrudModel> clazz;

    private DataTabs dataTabs;

    public DataCrud(){
        super();
        setWidth(100,Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);

        Window window = new Window("Text");
        window.center();
        window.setHeight(95,Unit.PERCENTAGE);
        window.setWidth(95, Unit.PERCENTAGE);
        window.setVisible(true);
        final FormLayout content = new FormLayout();
        window.setContent(content);
        UI.getCurrent().addWindow(window);

        this.setHeight(100, Unit.PERCENTAGE);
        this.setWidth(100, Unit.PERCENTAGE);
        searchPanel = new SearchPanel(User.class);
        resultsTable = new ResultsTable<User>(User.class);
        this.setMargin(true);
        this.addComponent(searchPanel);
        this.setExpandRatio(searchPanel, new Float(0.33));
        this.addComponent(resultsTable);
        this.setExpandRatio(resultsTable, new Float(0.67));

    }

    public ResultsTable getResultsTable() {
        return resultsTable;
    }

    public void setResultsTable(ResultsTable resultsTable) {
        this.resultsTable = resultsTable;
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public void setSearchPanel(SearchPanel searchPanel) {
        this.searchPanel = searchPanel;
    }

    public Class<? extends VcrudModel> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends VcrudModel> clazz) {
        this.clazz = clazz;
    }

    public void updateDataCrud(Class <? extends VcrudModel> clazz){
        this.clazz=clazz;
        searchPanel.setClazz(clazz);
        searchPanel.buildFields();

        dataTabs.setClazz(clazz);
        dataTabs.buildTabs();
    }

    public DataTabs getDataTabs() {
        return dataTabs;
    }

    public void setDataTabs(DataTabs dataTabs) {
        this.dataTabs = dataTabs;
    }
}
