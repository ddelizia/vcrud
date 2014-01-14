package org.ddelizia.vcrud.test;

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
@Document(collection = "SimpleItem")
public class SimpleItem extends VcrudItem implements VcrudTenantItem {

    private String code;

    private Integer i;

    public SimpleItem() {
    }

    public SimpleItem(String code, Integer i) {
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
}
