package org.ddelizia.vcrud.core.media.image.model.data;

import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 19:41
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class ProcessData {
    private ImageResizeData imageResizeData;

    public ImageResizeData getImageResizeData() {
        return imageResizeData;
    }

    public void setImageResizeData(ImageResizeData imageResizeData) {
        this.imageResizeData = imageResizeData;
    }
}
