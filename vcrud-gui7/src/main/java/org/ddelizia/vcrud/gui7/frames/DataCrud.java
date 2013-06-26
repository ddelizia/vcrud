package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Accordion;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.model.VcrudModel;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/06/13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public class DataCrud extends Accordion{

    private ResultsTable resultsTable;

    private SearchPanel searchPanel;

    private Class<? extends VcrudModel> clazz;

    private DataTabs dataTabs;

    public DataCrud(){
        super();
        setSizeFull();

        VerticalLayout verticalLayout1 = new VerticalLayout();
        verticalLayout1.setSizeFull();
        //verticalLayout1.addComponent(searchPanel);
        //verticalLayout1.addComponent(resultsTable);
        addTab(verticalLayout1,"Caption");

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
