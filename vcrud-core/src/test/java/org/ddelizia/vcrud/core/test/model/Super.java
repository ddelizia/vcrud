package org.ddelizia.vcrud.core.test.model;

import org.ddelizia.vcrud.model.basic.MultilanguageString;
import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.ddelizia.vcrud.model.basic.VcrudTenantItem;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 13/01/14
 * Time: 09:46
 * To change this template use File | Settings | File Templates.
 */
@Document(collection = "Super")
public class Super extends VcrudItem implements VcrudTenantItem {

    private String code;

    private Integer i;

    private MultilanguageString multilanguageString = new MultilanguageString();

    public Super() {
    }

    public Super(String code, Integer i) {
        this.code = code;
        this.i = i;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public MultilanguageString getMultilanguageString() {
        return multilanguageString;
    }

    public void setMultilanguageString(MultilanguageString multilanguageString) {
        this.multilanguageString = multilanguageString;
    }
}
