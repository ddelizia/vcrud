package org.ddelizia.vcrud.model.basic;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 8/05/13
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */

@Document(collection = "VcrudItem")
public abstract class VcrudItem {

    @Id
    private String id;

    private Date creation;

    private Date lastModification;


}
