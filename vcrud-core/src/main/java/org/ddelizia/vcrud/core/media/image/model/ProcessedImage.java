package org.ddelizia.vcrud.core.media.image.model;

import org.ddelizia.vcrud.core.media.image.model.data.ProcessData;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 12:10
 * To change this template use File | Settings | File Templates.
 */
public class ProcessedImage {

    @DBRef
    private Image original;

    @DBRef
    private Image image;

    private ProcessData processData;

    public Image getOriginal() {
        return original;
    }

    public void setOriginal(Image original) {
        this.original = original;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ProcessData getProcessData() {
        return processData;
    }

    public void setProcessData(ProcessData processData) {
        this.processData = processData;
    }
}
