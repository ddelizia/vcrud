package org.ddelizia.vcrud.model.media;

import org.ddelizia.vcrud.model.VcrudModel;
import org.ddelizia.vcrud.model.annotation.VcrudItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 2/07/13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@VcrudItem(parent = "Media", label = "ImagePreset", group = "Media")
public class Media extends VcrudModel {

    @Column(name="code")
    private String code;

    @Column(name="relativePath")
    private String relativePath;

    @Column(name="ext")
    private String ext;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }
}
