package org.ddelizia.vcrud.model.language;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import java.util.Locale;

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

    @Column(name = "key_id", unique = true)
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
        if (translation == null){
            translation = new MultilanguageString();
        }
        return translation;
    }

    public void setTranslation(MultilanguageString translation) {
        this.translation = translation;
    }

    public void addTranslation(Locale locale, String txt){
        getTranslation().addString(locale.getISO3Language(), txt);
    }

    public void addTranslation(String locale, String txt){
        getTranslation().addString(locale, txt);
    }
}
