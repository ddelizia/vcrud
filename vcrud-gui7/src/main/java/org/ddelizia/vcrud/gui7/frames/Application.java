package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Runo;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 14/05/13
 * Time: 15:06
 * To change this template use File | Settings | File Templates.
 */
public class Application extends VerticalLayout {


    public Application() {
        super();
        setWidth(100,Unit.PERCENTAGE);
        setSizeUndefined();
        addComponent(new SearchBox());
        addComponent(new ResultsBox());
    }
}
