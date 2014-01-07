package org.ddelizia.vcrud.model.usermanagement;

import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:13
 * To change this template use File | Settings | File Templates.
 */
public class Employee extends User {

    @DBRef
    private List<UserGroupEmployee> userGroupEmployees;

    private System system;

}
