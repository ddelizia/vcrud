package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.AbsoluteLayout;
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
    }
}
