package org.ddelizia.vcrud.core.service.impl;

import org.ddelizia.vcrud.core.service.ImageService;
import org.ddelizia.vcrud.core.service.ModelService;
import org.ddelizia.vcrud.model.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ModelService modelService;

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
                String pathToFile = storeMedia(multipartFile.getBytes(),"" +image.getId());
                image.setRelativePath(pathToFile.replace(mediaDirectory,""));
                modelService.persist(image);
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private String storeMedia(byte[] file,String filename){
        String pathToFile = null;

        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(mediaDatePath);
        String directoryName = simpleDateFormat.format(now);
        String directory = mediaDirectory;
        if(!directory.endsWith("/")){
            directory += "/";
        }
        directory+=mediaDirectory;

        try {
            pathToFile =  generateDirectory(mediaDirectory+"/")+filename;
            FileOutputStream fileOutputStream = new FileOutputStream (new File(pathToFile));
            fileOutputStream.write(file);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return pathToFile;

    }

    private String generateDirectory(String startDirectory){
        File directoryFile = new File(startDirectory);
        if (!directoryFile.isDirectory()){
            if(directoryFile.isFile()){
                generateDirectory(startDirectory+(Math.random()*1000));
            }else {
                directoryFile.mkdir();
                return startDirectory;
            }
        }else{
            return startDirectory;
        }

        return startDirectory;
    }
}
