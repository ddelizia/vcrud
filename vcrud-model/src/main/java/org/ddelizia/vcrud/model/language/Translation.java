package org.ddelizia.vcrud.model.language;

import org.ddelizia.vcrud.model.VcrudModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/06/13
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Translation extends VcrudModel {

    @Column(name = "key", unique = true)
    private String key;

    @Embedded
    @AttributeOverrides({@AttributeOverride(name="encodedMultiLanguageString",column=@Column(name="translation"))})
    private MultilanguageString translation;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public MultilanguageString getTranslation() {
        return translation;
    }

    public void setTranslation(MultilanguageString translation) {
        this.translation = translation;
    }
}
