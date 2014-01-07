package org.ddelizia.vcrud.model.usermanagement;

import org.ddelizia.vcrud.model.basic.VcrudTenantItem;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:11
 * To change this template use File | Settings | File Templates.
 */
public class Customer extends User implements VcrudTenantItem{

    @DBRef
    private List<UserGroupCustomer> userGroupCustomers;

}
