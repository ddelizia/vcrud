package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.model.VcrudModel;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ResultsTable<T extends VcrudModel> extends Panel{



    private VerticalLayout verticalLayout = new VerticalLayout();
    private BeanContainer<Long,T> beenContainer;
    private ModelService modelService;

    public ResultsTable() {
        super("table");
        verticalLayout.setSizeUndefined();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        modelService = SpringContextHelper.getBean(ModelService.class);

        //List<T> list = modelService.getModels(T.class);

        this.setWidth(100, Unit.PERCENTAGE);
        this.setHeight(67, Unit.PERCENTAGE);
        this.setContent(verticalLayout);
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
        verticalLayout.addComponent(table);
        verticalLayout.addComponent(table.createControls());

    }

}
