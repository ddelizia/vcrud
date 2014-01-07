package org.ddelizia.vcrud.model.system;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 28/12/13
 * Time: 12:02
 * To change this template use File | Settings | File Templates.
 */
public class Tenant extends VcrudItem{

    @Indexed(unique = true)
    private String code;

    @Indexed(unique = true)
    private String dbName;

    private String username;

    private String password;

    @DBRef
    private TenantHost tenantHost;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public TenantHost getTenantHost() {
        return tenantHost;
    }

    public void setTenantHost(TenantHost tenantHost) {
        this.tenantHost = tenantHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
