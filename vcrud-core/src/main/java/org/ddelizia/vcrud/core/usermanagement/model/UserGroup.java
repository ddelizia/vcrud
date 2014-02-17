package org.ddelizia.vcrud.core.usermanagement.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/12/13
 * Time: 12:07
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "UserGroup")
public class UserGroup extends VcrudPrincipalGroup {

    @DBRef
    private Set<UserGroup> childs = new HashSet<>();

    @DBRef
    private UserGroup father;

    public Set<UserGroup> getChilds() {
        return childs;
    }

    public void setChilds(Set<UserGroup> childs) {
        this.childs = childs;
    }

    public UserGroup getFather() {
        return father;
    }

    public void setFather(UserGroup father) {
        this.father = father;
    }
}
