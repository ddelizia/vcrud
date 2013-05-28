package org.ddelizia.vcrud.gui7.config;

import org.springframework.context.ApplicationContext;
import org.springframework.ui.context.support.UiApplicationContextUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/05/13
 * Time: 13:48
 * To change this template use File | Settings | File Templates.
 */
public class SpringContextHelper {

    private ApplicationContext context;
    public SpringContextHelper(ServletContext servletContext) {
        /*ServletContext servletContext =
                ((WebApplicationContext) application.getContext())
                .getHttpSession().getServletContext();*/
        context = WebApplicationContextUtils.
                getRequiredWebApplicationContext(servletContext);
    }

    public Object getBean(final String beanRef) {
        return context.getBean(beanRef);
    }

    public <T> T getBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }
}
