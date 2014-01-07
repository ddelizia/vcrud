package org.ddelizia.vcrud.model.system;

import org.ddelizia.vcrud.model.basic.VcrudItem;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 6/05/13
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */

@Document
public class Attribute extends VcrudItem {

    /**
     * Composed by classname.propertyname
     */
    @Indexed(unique = true)
    private String code;

    private String aClass;

    private String name;

    private String simpleName;

    @DBRef
    private Type type;

}
