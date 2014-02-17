package org.ddelizia.vcrud.core.i18n.model;

import org.ddelizia.vcrud.core.basic.provider.RequestProvider;

import javax.persistence.Entity;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 10/01/14
 * Time: 17:43
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class LocalizedString {

    private Locale locale;

    private String value;

    public LocalizedString() {
    }

    public LocalizedString(Locale locale, String value) {
        this.locale = locale;
        this.value = value;
    }

    public LocalizedString(String value) {
        this.locale = RequestProvider.getRequestLocale();
        this.value = value;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
