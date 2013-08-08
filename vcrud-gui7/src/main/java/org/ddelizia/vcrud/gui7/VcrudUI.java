package org.ddelizia.vcrud.gui7;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.ddelizia.vcrud.gui7.frames.Footer;
import org.ddelizia.vcrud.gui7.frames.Header;
import org.ddelizia.vcrud.gui7.frames.scaffolding.ScaffoldingApp;

import java.util.logging.Logger;

/**
 * The ScaffoldingApp's "main" class
 */
@SuppressWarnings("serial")
@Theme("vcrud-theme")
public class VcrudUI extends UI
{
    private final static Logger LOGGER = Logger.getLogger(VcrudUI.class.getName());

    private final static int LEFT_MARGIN=0;
    private final static int RIGHT_MARGIN=0;

    private AbsoluteLayout mainLayout=new AbsoluteLayout();

    private Header header;
    private Layout center;
    private Footer footer;

    @Override
    protected void init(VaadinRequest request) {

        request.getLocale();
        LOGGER.info("Current Locale" + request.getLocale().getISO3Country());

        mainLayout.setSizeFull();

        init();

        mainLayout.addComponent(header,"left: "+LEFT_MARGIN+"px; right: "+RIGHT_MARGIN+"px; top: 0px; ");
        mainLayout.addComponent(center,"left: "+LEFT_MARGIN+"px; right: "+RIGHT_MARGIN+"px; top: "+Header.HEIGHT+"px; bottom: "+Footer.HEIGHT+";");
        mainLayout.addComponent(footer,"left: "+LEFT_MARGIN+"px; right: "+RIGHT_MARGIN+"px; bottom: 0px; height:"+Footer.HEIGHT+";");
        setContent(mainLayout);
    }

    private void init(){
        header = new Header();
        footer = new Footer();
        center = new VerticalLayout();
        center.addComponent(new ScaffoldingApp());
        center.setSizeFull();
    }

    public Layout getCenter() {
        return center;
    }

    public void setCenter(Layout center) {
        this.center = center;
    }

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Footer getFooter() {
        return footer;
    }

    public void setFooter(Footer footer) {
        this.footer = footer;
    }
}
