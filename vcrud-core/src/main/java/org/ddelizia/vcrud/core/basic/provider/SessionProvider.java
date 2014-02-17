package org.ddelizia.vcrud.core.basic.provider;

import javax.servlet.http.HttpSession;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 9/05/13
 * Time: 12:28
 * To change this template use File | Settings | File Templates.
 */
public class SessionProvider {

    public static HttpSession getSession() {
        return RequestProvider.getRequest().getSession(true);
    }

    public static HttpSession setSessionAttribute(String key, Object value){
        getSession().setAttribute(key, value);
        return getSession();
    }

    public static Object getSessionAttributeValue(String key){
        return getSession().getAttribute(key);
    }
}
