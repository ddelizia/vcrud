package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.*;
import org.ddelizia.vcrud.model.VcrudModel;


/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 20/05/13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Header extends VerticalLayout {

    public static final int HEIGHT=100;
    public static final int RIGHT_LAYOUT = 200;

    public Header() {
        super();
        setSizeFull();
        setHeight(HEIGHT,Unit.PIXELS);
        setWidth(100,Unit.PERCENTAGE);
        setStyleName("vcrud-header");

        Label title = new Label ("Vcrud");
        title.setStyleName("vcrud-title");
        title.setSizeUndefined();

        Label subTitle = new Label ("This is Vcrud description");
        subTitle.setStyleName("vcrud-subtitle");
        subTitle.setWidth(100,Unit.PERCENTAGE);

        VerticalLayout topLeftLayout = new VerticalLayout();
        topLeftLayout.setSizeFull();
        //topLeftLayout.setMargin(true);
        topLeftLayout.addComponent(title);
        topLeftLayout.setComponentAlignment(title,Alignment.BOTTOM_LEFT);
        topLeftLayout.addComponent(subTitle);

        VerticalLayout topRightLayout = new VerticalLayout();
        topRightLayout.setMargin(true);
        topRightLayout.setWidth(RIGHT_LAYOUT,Unit.PIXELS);
        ComboBox comboLanguage = new ComboBox();
        comboLanguage.setWidth(100,Unit.PERCENTAGE);
        Label descriptionText = new Label("hola hola");
        descriptionText.setWidth(100,Unit.PERCENTAGE);

        topRightLayout.addComponent(descriptionText);
        topRightLayout.setComponentAlignment(descriptionText, Alignment.MIDDLE_LEFT);
        topRightLayout.addComponent(comboLanguage);
        topRightLayout.setComponentAlignment(comboLanguage, Alignment.MIDDLE_LEFT);

        AbsoluteLayout headerTopLayout = new AbsoluteLayout();
        headerTopLayout.setSizeFull();
        headerTopLayout.addComponent(topLeftLayout,"left: 10px; right: "+RIGHT_LAYOUT+";");
        headerTopLayout.addComponent(topRightLayout,"right: 0px;");

        addComponent(headerTopLayout);
        setComponentAlignment(headerTopLayout, Alignment.BOTTOM_LEFT);
        setExpandRatio(headerTopLayout,75.00f);

        MenuBar menubar = new MenuBar();
        menubar.setAutoOpen(true);
        menubar.setWidth(100,Unit.PERCENTAGE);
        addComponent(menubar);
        setComponentAlignment(menubar, Alignment.BOTTOM_LEFT);
        setExpandRatio(menubar,25.00f);

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
