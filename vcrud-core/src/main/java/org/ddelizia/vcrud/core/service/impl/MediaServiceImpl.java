package org.ddelizia.vcrud.core.service.impl;

import org.apache.commons.io.FileUtils;
import org.ddelizia.vcrud.core.service.MediaService;
import org.ddelizia.vcrud.model.Media;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/07/13
 * Time: 16:16
 * To change this template use File | Settings | File Templates.
 */

@Service("org.ddelizia.vcrud.core.service.MediaService")
public class MediaServiceImpl implements MediaService{

    @Value("${media.directory}")
    private String mediaDirectory;

    @Value("${media.datePath}")
    private String mediaDatePath;

    public byte[] getMediaToByteArray(Media image){
        String filePath = getMediaPath(image);
        try {
            return FileUtils.readFileToByteArray(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public String storeMedia(byte[] file,String filename){
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
            pathToFile =  generateDirectory(mediaDirectory)+"/"+filename;
            FileOutputStream fileOutputStream = new FileOutputStream (new File(pathToFile));
            fileOutputStream.write(file);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return pathToFile;

    }

    public String getMediaPath(Media media){
        return mediaDatePath+media.getRelativePath();
    }

    public String generateDirectory(String startDirectory){
        return generateDirectory(startDirectory,null);
    }

    public String generateDirectory(String startDirectory, Integer index){
        File directoryFile=null;
        if (index == null){
            directoryFile = new File(startDirectory);
            index=1;
        }else{
            directoryFile  = new File(startDirectory + index);
            index++;
        }
        if (!directoryFile.isDirectory()){
            if(directoryFile.isFile()){
                generateDirectory(startDirectory+index);
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
