package org.ddelizia.vcrud.core.web.filter;

import org.ddelizia.vcrud.core.config.ApplicationContextProvider;
import org.ddelizia.vcrud.core.service.system.DomainService;
import org.ddelizia.vcrud.model.system.Domain;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/09/13
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class DomainFilter extends OncePerRequestFilter{

    private DomainService domainService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        Domain domain = domainService.getCurrentDomain();
        if (domain == null){
            domainService.setCurrentDomain(domainService.getDomainFromUrl(new URL(httpServletRequest.getRequestURL().toString())));
        }
    }

}
