package org.ddelizia.vcrud.model.media;

import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 16:15
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(parent = "Media", label = "ImageCache", group = "Media")
public class ImageCache extends VcrudModel{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imagePreset_ref")
    private ImagePreset imagePreset;

    @OneToOne
    private Image image;

    @Column(name = "path")
    private String imagePath;

    public ImagePreset getImagePreset() {
        return imagePreset;
    }

    public void setImagePreset(ImagePreset imagePreset) {
        this.imagePreset = imagePreset;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
