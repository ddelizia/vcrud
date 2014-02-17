package org.ddelizia.vcrud.core.media.image.repository;

import org.ddelizia.vcrud.core.media.image.model.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 19:05
 * To change this template use File | Settings | File Templates.
 */
public interface ImageRepository extends MongoRepository<Image, String>{
}
