package org.ddelizia.vcrud.core.service.system.impl;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import org.ddelizia.vcrud.core.config.SessionAttribute;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.system.DomainService;
import org.ddelizia.vcrud.core.service.web.RequestService;
import org.ddelizia.vcrud.model.system.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/09/13
 * Time: 9:50
 * To change this template use File | Settings | File Templates.
 */

@Service("vcrudDomainService")
public class DomainServiceImpl implements DomainService{

    @Autowired
    private ModelService modelService;

    @Autowired
    private RequestService requestService;

    @Override
    public Domain getDomainFromUrl(final URL url) {
        List<Domain> domains = modelService.getModels(Domain.class, null, null);
        Collection<Domain> result = Collections2.filter(domains, new Predicate<Domain>() {
            @Override
            public boolean apply(Domain domain) {
                return url.toString().matches(domain.getUrlRegex());
            }
        });
        if (result.size()>1){
            throw new NonUniqueResultException();
        }
        return Iterables.get(result,0);
    }

    @Override
    public Domain getCurrentDomain() {
        Object o = requestService.getHttpSession().getAttribute(SessionAttribute.DOMAIN_CODE);
        if (o !=null){
            //return modelService.getModel(Domain_.code.getName(), (String)o, Domain.class);
        }
        return null;
    }

    @Override
    public void setCurrentDomain(Domain domain) {
        requestService.getHttpSession().setAttribute(SessionAttribute.DOMAIN_CODE, domain.getCode());
    }

}
