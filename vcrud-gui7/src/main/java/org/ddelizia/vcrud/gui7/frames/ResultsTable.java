package org.ddelizia.vcrud.gui7.frames;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ResultsTable extends VerticalLayout{


    public ResultsTable() {
        super();
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty("foo", String.class, null);
        container.addContainerProperty("bar", String.class, null);
        container.addContainerProperty("baz", String.class, null);
        for (int i = 0; i < 100; i++) {
            Item item = container.addItem(i);
            item.getItemProperty("foo").setValue("foo " + i);
            item.getItemProperty("bar").setValue("bar");
            item.getItemProperty("baz").setValue("baz");
        }
        PagedTable table = new PagedTable("footable");
        table.setContainerDataSource(container);
        table.setPageLength(15);
        table.setSizeFull();
        table.setEditable(true);
        addComponent(table);
        addComponent(table.createControls());

    }
}
