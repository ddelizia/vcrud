package org.ddelizia.vcrud.core.service.impl;

import org.ddelizia.vcrud.core.service.ImageService;
import org.ddelizia.vcrud.core.service.MediaService;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */

@Service("org.ddelizia.vcrud.core.service.ImageService")
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ModelService modelService;

    @Autowired
    private MediaService mediaService;

    @Value("${media.directory}")
    private String mediaDirectory;

    @Value("${media.datePath}")
    private String mediaDatePath;

    @Transactional
    public void storeImage(MultipartFile multipartFile){

        try {
            if(multipartFile.getContentType().startsWith("image")){
                Image image = modelService.create(Image.class);
                image.setExt(multipartFile.getName().substring(multipartFile.getName().lastIndexOf("."), multipartFile.getName().length()));
                String pathToFile = mediaService.storeMedia(multipartFile.getBytes(),"" +image.getId());
                image.setRelativePath(pathToFile.replace(mediaDirectory,""));
                modelService.persist(image);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }


}
