package org.ddelizia.vcrud.gui7.config;

import com.vaadin.server.VaadinServlet;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/05/13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class SpringContextHelper {

    private static  ApplicationContext context;

    protected SpringContextHelper(){

    }

    public static ApplicationContext getApplicationContext() {
        if (context==null){
            context = WebApplicationContextUtils.getRequiredWebApplicationContext(VaadinServlet.getCurrent().getServletContext());
        }
        return context;
    }

    public static <T extends Object> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

}
