package org.ddelizia.vcrud.media.service;

import org.ddelizia.vcrud.media.model.Media;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 10/07/13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
public interface MediaService {

    public byte[] getMediaToByteArray(Media image);

    public String storeMedia(byte[] file,String filename);

    public String getMediaPath(Media media);

    public String generateDirectory(String startDirectory);

    public Media storeMedia(byte[] file, String filename, String media);
}
