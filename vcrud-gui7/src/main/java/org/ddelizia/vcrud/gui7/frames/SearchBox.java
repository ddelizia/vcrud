package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.gui7.component.CollapsableLayout;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class SearchBox extends CollapsableLayout{

    public SearchBox() {
        super(new VerticalLayout(), "Search");
        getLayout().setVisible(true);
        getLayout().addComponent(new Label("Test Search"));
    }
}
