package org.ddelizia.vcrud.core.i18n.model;

import org.ddelizia.vcrud.core.basic.provider.RequestProvider;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 14/01/14
 * Time: 10:23
 * To change this template use File | Settings | File Templates.
 */

@Entity
public class MultilanguageString {

    private Map<Locale, LocalizedString> map = new HashMap<>();

    public Map<Locale, LocalizedString> getMap() {
        return map;
    }

    public void setMap(Map<Locale, LocalizedString> map) {
        this.map = map;
    }

    public void addLocalizedString (LocalizedString localizedString){
        map.put(localizedString.getLocale(), localizedString);
    }

    public LocalizedString removeLocalizedStringWithLocale(Locale locale){
        return map.remove(locale);
    }

    public void addCurrentLocale(String value){
        map.put(RequestProvider.getRequestLocale(), new LocalizedString(RequestProvider.getRequestLocale(), value));
    }

    public String getCurrentLocale(){
        return map.get(RequestProvider.getRequestLocale()).getValue();
    }

}
