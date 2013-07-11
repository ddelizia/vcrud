package org.ddelizia.vcrud.core.service;

import org.ddelizia.vcrud.model.Media;

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
}
