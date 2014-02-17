package org.ddelizia.vcrud.core.media.image.model.data;

import org.ddelizia.vcrud.core.media.image.processor.resize.ResizeEnum;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 18:55
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class ImageResizeData{

    private int width;
    private int heigh;

    private ResizeEnum resizeEnum;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }

    public ResizeEnum getResizeEnum() {
        return resizeEnum;
    }

    public void setResizeEnum(ResizeEnum resizeEnum) {
        this.resizeEnum = resizeEnum;
    }
}
