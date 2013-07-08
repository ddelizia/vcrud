package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 20/05/13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Header extends VerticalLayout {

    public static final int HEIGHT=100;

    public Header() {
        super();
        setSizeFull();
        setHeight(HEIGHT,Unit.PIXELS);
        setWidth(100,Unit.PERCENTAGE);
        //setMargin(true);

        //setComponentAlignment(selection,Alignment.TOP_RIGHT);

        MenuBar menubar = new MenuBar();
        menubar.setAutoOpen(true);
        menubar.setWidth(100,Unit.PERCENTAGE);
        addComponent(menubar);
        setComponentAlignment(menubar, Alignment.BOTTOM_LEFT);

        MenuBar.MenuItem dataMenuItem = menubar.addItem("Data",new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {

            }
        });

        MenuBar.MenuItem script = menubar.addItem("Script",null);

        MenuBar.MenuItem impExp = script.addItem("ImportExport",new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
            }
        });

        MenuBar.MenuItem manageSystem = script.addItem("ManageSystem",new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {
            }
        });

        MenuBar.MenuItem query = script.addItem("EntityQuery",new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem menuItem) {

            }
        });



    }
}
