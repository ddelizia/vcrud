package org.ddelizia.vcrud.core.basic.provider;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class RequestProvider {

    public static HttpServletRequest getRequest() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return attr.getRequest();
    }

    public static Locale getRequestLocale(){
        return getRequest().getLocale();
    }

    public static String getServerName(){
        return getRequest().getServerName();
    }

    public static String getContextPath(){
        return getRequest().getContextPath();
    }

    public static String getQueryString(){
        return getRequest().getQueryString();
    }

    public static String getProtocol(){
        return getRequest().getProtocol();
    }

    public static Integer getLocalPort(){
        return getRequest().getLocalPort();
    }

}
