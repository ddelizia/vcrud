package org.ddelizia.vcrud.model.usermanagement;

import org.ddelizia.vcrud.model.basic.VcrudTenantItem;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "UserGroup")
public class UserGroup extends VcrudPrincipalGroup{
}
