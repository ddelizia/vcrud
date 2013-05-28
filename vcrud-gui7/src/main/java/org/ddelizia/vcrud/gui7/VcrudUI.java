package org.ddelizia.vcrud.gui7;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.gui7.config.SpringContextHelper;
import org.ddelizia.vcrud.gui7.frames.Footer;
import org.ddelizia.vcrud.gui7.frames.Header;
import org.ddelizia.vcrud.gui7.frames.Menu;
import org.ddelizia.vcrud.gui7.frames.Application;

import java.util.logging.Logger;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class VcrudUI extends UI
{
    private final static Logger LOGGER = Logger.getLogger(VcrudUI.class.getName());

    private Menu menu;
    private Application application;
    private Header header;
    private Footer footer;

    @Override
    protected void init(VaadinRequest request) {

        SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());

        org.ddelizia.vcrud.core.service.ModelService modelService=helper.getBean(ModelService.class);

        request.getLocale();
        LOGGER.info("Current Locale" + request.getLocale().getISO3Country());

        //Panel panel = new Panel("Test");
        //panel.setSizeFull();

        AbsoluteLayout mainLayout=new AbsoluteLayout();
        //mainLayout.setMargin(true);
        mainLayout.setSizeFull();
        //mainLayout.setHeight("auto");
        //mainLayout.setWidth(100,Unit.PERCENTAGE);
        //mainLayout.setSizeUndefined();

        init();

        HorizontalSplitPanel center = new HorizontalSplitPanel(menu,application);
        center.setSplitPosition(200, Unit.PIXELS);


        mainLayout.addComponent(header,"left: 0px; top: 0px;");
        mainLayout.addComponent(center,"left: 0px; top: "+Header.HEIGHT+"px; bottom: "+Footer.HEIGHT+";");
        mainLayout.addComponent(footer,"left: 0px; bottom: "+Footer.HEIGHT+";" +
                "");
        //mainLayout.addComponent(footer);

        //panel.setContent(mainLayout);

        setContent(mainLayout);
    }

    private void init(){
        menu = new Menu();
        header = new Header();
        footer = new Footer();
        application = new Application();
    }

}
