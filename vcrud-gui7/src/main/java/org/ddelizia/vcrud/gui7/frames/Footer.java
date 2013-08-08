package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 20/05/13
 * Time: 15:23
 * To change this template use File | Settings | File Templates.
 */
public class Footer extends VerticalLayout {

    public static final int HEIGHT=40;

    public Footer() {
        super();
        setStyleName("vcrud-footer");
        setHeight(HEIGHT,Unit.PIXELS);
        Label footerText = new Label("This is the text that goes as footer text");
        footerText.setStyleName("vcrud-footer_text");
        addComponent(footerText);
        setComponentAlignment(footerText, Alignment.MIDDLE_LEFT);
    }
}
