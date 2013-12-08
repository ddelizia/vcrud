package org.ddelizia.vcrud.email.model;

import org.ddelizia.vcrud.model.media.Media;
import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 26/10/13
 * Time: 17:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class EmailTemplate extends VcrudModel{

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column (name = "description")
    private String description;

    //@Column (name = "txtMedia")
    //private Media txtMedia;

    @Column (name = "txt")
    private String txt;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public Media getTxtMedia() {
//        return txtMedia;
//    }
//
//    public void setTxtMedia(Media txtMedia) {
//        this.txtMedia = txtMedia;
//    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
