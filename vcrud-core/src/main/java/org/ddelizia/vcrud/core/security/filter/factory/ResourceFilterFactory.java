package org.ddelizia.vcrud.core.security.filter.factory;

import com.sun.jersey.api.container.filter.RolesAllowedResourceFilterFactory;
import com.sun.jersey.api.model.AbstractMethod;
import com.sun.jersey.spi.container.ResourceFilter;
import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.security.filter.SecurityContextFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.List;

/**
 * Add the SecurityContextFilter to the list of Filters to apply to requests
 *
 * This factory is registered with the Web Context:
 *
 * <code>
 *     <init-param>
 *          <param-name>com.sun.jersey.spi.container.ResourceFilters</param-name>
 *          <param-value>com.porterhead.com.porterhead.rest.filter.ResourceFilterFactory</param-value>
 *      </init-param>
 * </code>
 *
 *
 * @author ddelizia
 * @since 16/02/14 18:56
 */
@Component
@Provider
public class ResourceFilterFactory extends RolesAllowedResourceFilterFactory {

    private static final Logger LOGGER = Logger.getLogger(ResourceFilterFactory.class);

    @Autowired
    private SecurityContextFilter securityContextFilter;

    @Override
    public List<ResourceFilter> create(AbstractMethod am) {
        List<ResourceFilter> filters = super.create(am);
        if (filters == null) {
            filters = new ArrayList<>();
        }
        List<ResourceFilter> securityFilters = new ArrayList<>(filters);
        //put the Security Filter first in line
        securityFilters.add(0, securityContextFilter);
        return securityFilters;
    }
}
