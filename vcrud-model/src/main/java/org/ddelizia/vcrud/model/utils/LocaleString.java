package org.ddelizia.vcrud.model.utils;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 27/06/13
 * Time: 17:05
 * To change this template use File | Settings | File Templates.
 */
public class LocaleString {

    private String language;

    private String text;

    public LocaleString(String language, String text) {
        this.language = language;
        this.text = text;
    }

    public LocaleString() {
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
