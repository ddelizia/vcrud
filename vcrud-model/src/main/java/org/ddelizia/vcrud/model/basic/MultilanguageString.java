package org.ddelizia.vcrud.model.basic;

import sun.util.calendar.LocalGregorianCalendar;

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
public class MultilanguageString {

    private Map<Locale, LocalizedString> map = new HashMap<>();

    public Map<Locale, LocalizedString> getMap() {
        return map;
    }

    public void setMap(Map<Locale, LocalizedString> map) {
        this.map = map;
    }
}
