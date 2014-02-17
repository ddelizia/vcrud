package org.ddelizia.vcrud.core.media.image.repository;

import org.ddelizia.vcrud.core.media.image.model.Image;
import org.ddelizia.vcrud.core.media.image.model.ProcessedImage;
import org.ddelizia.vcrud.core.media.image.model.data.ProcessData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public interface ProcessedImmageRepository extends MongoRepository<ProcessedImage, String>{

    public ProcessedImage findByProcessDataAndOriginal(ProcessData processData, Image original);

    @Query(value="{ 'processData' : ?0 , 'original._id' : ?1 }")
    public ProcessedImage findByProcessDataAndOriginalImageId(ProcessData processData, String imageId);
}
