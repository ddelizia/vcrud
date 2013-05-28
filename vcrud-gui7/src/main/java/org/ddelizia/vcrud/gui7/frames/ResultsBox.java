package org.ddelizia.vcrud.gui7.frames;

import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.gui7.component.CollapsableLayout;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/05/13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
public class ResultsBox extends CollapsableLayout{

    private VerticalLayout content;

    public ResultsBox() {
        super(new VerticalLayout(), "Results");
        content = (VerticalLayout)getLayout();
        content.setVisible(true);
        content.addComponent(new TextField("Text Field"));
    }
}
