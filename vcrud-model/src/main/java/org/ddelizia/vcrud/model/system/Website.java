package org.ddelizia.vcrud.model.system;

import org.ddelizia.vcrud.model.basic.LocalizedString;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/01/14
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */

@Document(collection = "Website")
public class Website extends VcrudItem {

    private String regex;

    private LocalizedString name;

    @Indexed(unique = true)
    private String code;

    @DBRef
    private Tenant tenant;

    public Website() {
    }

    public Website(String regex, LocalizedString name, String code, Tenant tenant) {
        this.regex = regex;
        this.name = name;
        this.code = code;
        this.tenant = tenant;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public LocalizedString getName() {
        return name;
    }

    public void setName(LocalizedString name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }
}
