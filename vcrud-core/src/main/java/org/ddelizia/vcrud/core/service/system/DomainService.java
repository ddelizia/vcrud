package org.ddelizia.vcrud.core.service.system;

import org.ddelizia.vcrud.model.system.Domain;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/09/13
 * Time: 9:47
 * To change this template use File | Settings | File Templates.
 */
public interface DomainService {
    
    public Domain getDomainFromUrl(URL url);

    public Domain getCurrentDomain();

    public void setCurrentDomain(Domain domain);
}
