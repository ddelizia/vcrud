package org.ddelizia.vcrud.core.service;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/06/13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
public interface TranslationService {

    /**
     * Retrive translation for locale
     * @param key
     * @param locale
     * @return
     */
    public String getTranslationForKey(String key, Locale locale);

}
