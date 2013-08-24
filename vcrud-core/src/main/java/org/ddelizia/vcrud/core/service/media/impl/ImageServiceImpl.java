package org.ddelizia.vcrud.core.service.media.impl;

import org.ddelizia.vcrud.core.service.media.ImageService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.model.media.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */

@Service("VcrudImageService")
public class ImageServiceImpl extends MediaServiceImpl implements ImageService {

    @Autowired
    private ModelService modelService;

    @Value("${media.directory}")
    private String mediaDirectory;

    @Value("${media.datePath}")
    private String mediaDatePath;

    @Transactional
    public Image storeImage(MultipartFile multipartFile){
        Image image = null;
        try {
            if(multipartFile.getContentType().startsWith("image")){
                image = new Image();
                image.setCode(multipartFile.getOriginalFilename() + "-" + UUID.randomUUID().toString());
                image.setExt(multipartFile.getName().substring(multipartFile.getName().lastIndexOf("."), multipartFile.getName().length()));
                String pathToFile = storeMedia(multipartFile.getBytes(), multipartFile.getOriginalFilename());
                image.setRelativePath(pathToFile.replace(mediaDirectory,""));
                modelService.persist(image);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return image;

    }


}
