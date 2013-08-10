package org.ddelizia.vcrud.model.media;

import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 15:50
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(parent = "Media", label = "ImagePreset", group = "Media")
public class ImagePreset {

    @Column(name = "type")
    private String type;

    @Column(name = "width")
    private String width;

    @Column(name = "height")
    private String height;

    @Column(name = "method")
    private String method;

    @Column(name = "mode")
    private String mode;

    @Column(name = "rotation")
    private String rotation;

    public class ImagePresetMethod{
        private static final String AUTOMATIC= "AUTOMATIC";
        private static final String SPEED= "SPEED";
        private static final String BALANCED= "BALANCED";
        private static final String QUALITY= "QUALITY";
        private static final String ULTRA_QUALITY= "ULTRA_QUALITY";
    }

    public class ImagePresetMode{
        private static final String AUTOMATIC= "AUTOMATIC";
        private static final String FIT_EXACT= "FIT_EXACT";
        private static final String FIT_TO_WIDTH= "FIT_TO_WIDTH";
        private static final String FIT_TO_HEIGHT= "FIT_TO_HEIGHT";
    }

    public class ImagePresetRotation{
        private static final String CW_90= "CW_90";
        private static final String CW_180= "CW_180";
        private static final String CW_270= "CW_270";
        private static final String FLIP_HORZ= "FLIP_HORZ";
        private static final String FLIP_VERT= "FLIP_VERT";
    }

    public class ImagePresetType{
        private static final String RESIZE= "RESIZE";
        private static final String CROP= "CROP";
        private static final String ROTATE= "ROTATE";
    }

}
