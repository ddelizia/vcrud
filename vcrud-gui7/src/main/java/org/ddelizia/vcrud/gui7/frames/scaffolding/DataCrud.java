package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.model.User;
import org.ddelizia.vcrud.model.VcrudModel;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class DataCrud extends TabSheet{

    private ResultsTable resultsTable;

    private SearchPanel searchPanel;

    private Class<? extends VcrudModel> clazz;

    private DataTabs dataTabs;

    public DataCrud(){
        super();
        setWidth(100,Unit.PERCENTAGE);
        setHeight(100, Unit.PERCENTAGE);

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.setSizeUndefined();
        verticalLayout1.setWidth(100, Unit.PERCENTAGE);
        searchPanel = new SearchPanel(User.class);
        resultsTable = new ResultsTable();
        verticalLayout1.setMargin(true);
        verticalLayout1.addComponent(searchPanel);
        verticalLayout1.addComponent(resultsTable);
        addTab(verticalLayout1, "Search");

        VerticalLayout verticalLayout2 = new VerticalLayout();
        verticalLayout2.setSizeFull();
        verticalLayout2.setMargin(true);
        for(int i=0; i<100; i++){
            verticalLayout2.addComponent(new Label("test"+i));
        }

        addTab(verticalLayout2, "Result");
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
