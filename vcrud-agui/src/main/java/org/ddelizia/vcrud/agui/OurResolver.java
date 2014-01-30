package org.ddelizia.vcrud.agui;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/01/14
 * Time: 19:37
 * To change this template use File | Settings | File Templates.
 */
public class OurResolver extends AbstractCachingViewResolver{
    @Override
    protected View loadView(String s, Locale locale) throws Exception {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
