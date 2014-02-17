package org.ddelizia.vcrud.core.media.image.processor.resize;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 12:50
 * To change this template use File | Settings | File Templates.
 */
public interface ResizeProcessor {

    public BufferedImage resizeImg(BufferedImage image, int width, int height);

}
