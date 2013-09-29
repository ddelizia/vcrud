package org.ddelizia.vcrud.core.service.media.impl;

import org.ddelizia.vcrud.core.service.media.ImageService;
import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.web.RequestService;
import org.ddelizia.vcrud.model.media.Image;
import org.ddelizia.vcrud.model.media.Image_;
import org.ddelizia.vcrud.model.media.Preset;
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

    @Autowired
    private RequestService requestService;

    @Value("${media.directory}")
    private String mediaDirectory;

    @Value("${media.datePath}")
    private String mediaDatePath;

    @Value("${media.image.rm_url_4code}")
    private String propertyRequestMapping4Code;

    @Value("${media.image.rm_url_4codepreset}")
    private String propertyRequestMapping4CodePreset;

    @Transactional
    @Override
    public Image storeImage(MultipartFile multipartFile){
        return storeImage(multipartFile, null);
    }

    @Override
    public Image storeImage(MultipartFile multipartFile, String code){
        Image image = null;
        try {
            if(multipartFile.getContentType().startsWith("image")){
                image = new Image();
                if(code == null){
                    image.setCode(multipartFile.getOriginalFilename() + "-" + UUID.randomUUID().toString());
                }else {
                    image.setCode(code);
                }
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

    @Override
    @Transactional
    public Image storeImage(byte[] bytes, String name) {
        Image image = null;
        image.setCode(name + "-" + UUID.randomUUID().toString());
        image.setExt(name.substring(name.lastIndexOf("."), name.length()));
        String pathToFile = storeMedia(bytes, name);
        image.setRelativePath(pathToFile.replace(mediaDirectory,""));
        modelService.persist(image);
        return image;
    }

    public String getImagePath(Image image){
        if (image == null){
            return null;
        }
        return requestService.getBasicRequestPath() + propertyRequestMapping4Code.replace("{imgCode}",image.getCode());
    }

    @Override
    public String getImagePath(String imageCode) {
        return getImagePath(modelService.getModel(Image_.code.getName(), imageCode, Image.class));
    }

    public String getImagePathForPreset (Image image, Preset preset){
        if (image == null || preset == null){
            return null;
        }
        return requestService.getBasicRequestPath() + propertyRequestMapping4Code.
                replace("{imgCode}",image.getCode()).
                replace("{presetCode}",preset.getCode());
    }




}
