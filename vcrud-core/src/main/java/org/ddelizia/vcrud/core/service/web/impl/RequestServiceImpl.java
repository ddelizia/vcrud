package org.ddelizia.vcrud.core.service.web.impl;

import org.apache.commons.lang.StringUtils;
import org.ddelizia.vcrud.core.service.web.RequestService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/08/13
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
@Service("vcrudRequestService")
public class RequestServiceImpl implements RequestService{

    @Override
    public HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes)requestAttributes).getRequest();
            return request;
        }
        return null;
    }

    @Override
    public Locale getCurrentLocale() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null){
            return httpServletRequest.getLocale();
        }else {
            return null;
        }
    }

    @Override
    public HttpSession getHttpSession() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null){
            return httpServletRequest.getSession();
        }else {
            return null;
        }
    }

    @Override
    public URL getRequestURL() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null){
            try {
                return new URL(httpServletRequest.getRequestURL().toString());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }else {
            return null;
        }
        return null;
    }

    @Override
    public String getBasicRequestPath() {
        HttpServletRequest httpServletRequest = getHttpServletRequest();
        if (httpServletRequest != null ){
            URL url = null;
            try {
                url = new URL(httpServletRequest.getRequestURL().toString());
                String path = "";
                path += url.getProtocol() + "://" + url.getHost();
                if (url.getPort()>0){
                    path +=":"+url.getPort();
                }
                if (StringUtils.isNotEmpty(httpServletRequest.getContextPath())){
                    path += httpServletRequest.getContextPath();
                }
                return path;
            } catch (MalformedURLException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }


        }else{
            return null;
        }
        return null;
    }


}
