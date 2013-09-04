package org.ddelizia.vcrud.core.service.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.URL;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/08/13
 * Time: 11:17
 * To change this template use File | Settings | File Templates.
 */
public interface RequestService {

    public HttpServletRequest getHttpServletRequest();

    public Locale getCurrentLocale();

    public HttpSession getHttpSession();

    public URL getRequestURL();

    public String getBasicRequestPath();
}
