package org.ddelizia.vcrud.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 3/07/13
 * Time: 15:44
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Image extends Media {

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "image2imagepreset")
    private Collection<ImagePreset> presets;

    public Collection<ImagePreset> getPresets() {
        return presets;
    }

    public void setPresets(Collection<ImagePreset> presets) {
        this.presets = presets;
    }
}
