package org.ddelizia.vcrud.gui7.frames.scaffolding;

import com.jensjansson.pagedtable.PagedTable;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.component.search.EntityQueryFactory;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.model.VcrudModel;
import org.vaadin.addons.lazyquerycontainer.EntityContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.peter.contextmenu.ContextMenu;

import javax.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ResultsTable<T extends VcrudModel> extends Panel{

    private VerticalLayout verticalLayout = new VerticalLayout();
    private ModelService modelService;
    private Class<? extends VcrudModel> theClass;
    
    private EntityManager entityManager;
    private LazyQueryContainer lazyQueryContainer;
    private EntityContainer<T> entityContainer;

    public ResultsTable(Class <T> theClass) {
        super("table");
        verticalLayout.setSizeUndefined();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        modelService = SpringContextHelper.getBean(ModelService.class);
        this.theClass=theClass;
        entityManager = modelService.getEntityManager();



        EntityQueryFactory <T> entityQueryFactory = new EntityQueryFactory<T>(theClass);

        lazyQueryContainer =new LazyQueryContainer(null,entityQueryFactory);

        //lazyQueryContainer.addContainerProperty("id", String.class, "", true, true);
        //lazyQueryContainer.addContainerProperty("username", String.class, "", true, true);

        this.setWidth(100, Unit.PERCENTAGE);
        this.setHeight(67, Unit.PERCENTAGE);
        this.setContent(verticalLayout);

        PagedTable table = new PagedTable("footable");
        table.setContainerDataSource(lazyQueryContainer);
        table.setPageLength(15);
        table.setSizeFull();
        table.setSelectable(true);
        table.setEditable(false);
        table.setWidth(100, Unit.PERCENTAGE);

        ContextMenu.ContextMenuOpenedListener.TableListener tableOpenListener = new ContextMenu.ContextMenuOpenedListener.TableListener() {


            @Override
            public void onContextMenuOpenFromRow(ContextMenu.ContextMenuOpenedOnTableRowEvent contextMenuOpenedOnTableRowEvent) {
                System.out.println("Test");//To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onContextMenuOpenFromHeader(ContextMenu.ContextMenuOpenedOnTableHeaderEvent contextMenuOpenedOnTableHeaderEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void onContextMenuOpenFromFooter(ContextMenu.ContextMenuOpenedOnTableFooterEvent contextMenuOpenedOnTableFooterEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };



        ContextMenu contextMenu = new ContextMenu();
        contextMenu.setAsContextMenuOf(table);
        contextMenu.addItem("Delete");
        contextMenu.addItem("Modify");

        contextMenu.addContextMenuTableListener(tableOpenListener);


        verticalLayout.addComponent(table);
        verticalLayout.addComponent(table.createControls());

    }


}
