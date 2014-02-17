package org.ddelizia.vcrud.core.media.image.processor.resize.impl;

import org.ddelizia.vcrud.core.media.image.processor.resize.ResizeProcessor;
import org.imgscalr.Scalr;

import java.awt.image.BufferedImage;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 13:05
 * To change this template use File | Settings | File Templates.
 */
public class ResizeProcessorFitToWidth implements ResizeProcessor{

    @Override
    public BufferedImage resizeImg(BufferedImage image, int width, int height) {
        return Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH,
                width, height, Scalr.OP_ANTIALIAS);
    }

}
