package org.ddelizia.vcrud.gui7;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import org.apache.commons.logging.Log;
import org.ddelizia.vcrud.gui7.window.LoginWindow;

import java.util.logging.Logger;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class VcrudUI extends UI
{
    private final static Logger LOGGER = Logger.getLogger(VcrudUI.class.getName());

    @Override
    protected void init(VaadinRequest request) {
        request.getLocale();
        LOGGER.info("Current Locale" + request.getLocale().getISO3Country());

        addWindow(new LoginWindow());
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Button button = new Button("Click Me");
        button.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                layout.addComponent(new Label("Thank you for clicking"));
            }
        });
        layout.addComponent(button);

        Table table = new Table("This is my Table");

/* Define the names and data types of columns.
 * The "default value" parameter is meaningless here. */
        table.addContainerProperty("First Name", String.class,  null);
        table.addContainerProperty("Last Name",  String.class,  null);
        table.addContainerProperty("Year",       Table.class, null);

/* Add a few items in the table. */
        table.addItem(new Object[] {
                "Nicolaus","Copernicus",buildInnerTable()}, new Integer(1));
        table.addItem(new Object[] {
                "Tycho",   "Brahe",     buildInnerTable()}, new Integer(2));
        table.setWidth("100%");
        layout.addComponent(table);
    }

    private Table buildInnerTable(){
        final VerticalLayout layout = new VerticalLayout();

        Table table = new Table("This is my Table");

/* Define the names and data types of columns.
 * The "default value" parameter is meaningless here. */
        table.addContainerProperty("First Name", String.class,  null);
        table.addContainerProperty("Last Name",  String.class,  null);
        table.addItem(new Object[]{
                "Nicolaus", "Copernicus"}, new Integer(3));

        layout.addComponent(table);
        return table;
    }

}
