package org.ddelizia.vcrud.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/06/13
 * Time: 10:13
 * To change this template use File | Settings | File Templates.
 */
public class Translation {

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
