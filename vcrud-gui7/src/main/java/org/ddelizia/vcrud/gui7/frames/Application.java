package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.HorizontalSplitPanel;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 14/05/13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class Application extends HorizontalSplitPanel {

    private Menu menu;
    private DataCrud dataCrud;

    public Application() {
        super();
        menu = new Menu();
        dataCrud = new DataCrud();
        setFirstComponent(menu);
        setSecondComponent(dataCrud);
        setSplitPosition(200, Unit.PIXELS);
        setWidth(100,Unit.PERCENTAGE);
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public DataCrud getDataCrud() {
        return dataCrud;
    }

    public void setDataCrud(DataCrud dataCrud) {
        this.dataCrud = dataCrud;
    }
}
