package org.ddelizia.vcrud.model.system;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 02/01/14
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
@Document(collection = "TenantHost")
public class TenantHost extends VcrudItem{

    @Indexed(unique = true)
    private String code;

    private String host;

    private Integer port;

    public TenantHost() {
    }

    public TenantHost(String code, String host, Integer port) {
        this.code = code;
        this.host = host;
        this.port = port;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}
