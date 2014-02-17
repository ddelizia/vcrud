package org.ddelizia.vcrud.core.media.image.model;

import org.ddelizia.vcrud.core.media.model.Media;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 11:39
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Document(collection = "Image")
public class Image extends Media {


}
