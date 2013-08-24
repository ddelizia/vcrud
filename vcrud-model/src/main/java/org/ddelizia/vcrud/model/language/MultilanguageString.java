package org.ddelizia.vcrud.model.language;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ddelizia.vcrud.model.utils.LocaleString;

import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 27/06/13
 * Time: 18:15
 * To change this template use File | Settings | File Templates.
 */

@Embeddable
public class MultilanguageString {

    @Transient
    private Gson gson;

    private String encodedMultiLanguageString="[]";

    public MultilanguageString(){

    }

    public MultilanguageString(Locale locale, String txt){
        addString(locale.getISO3Language(),txt);
    }

    public MultilanguageString(String locale, String txt){
        addString(locale, txt);
    }

    /**
     * Get localized String
     * @param locale
     * @return
     */
    public String getString (String locale){
        ArrayList <LocaleString> list = new ArrayList<LocaleString>(Arrays.asList(convertFromGson()));
        LocaleString localeString = new LocaleString(locale,"");

        return list.get(list.indexOf(localeString)).getText();

    }

    public List<LocaleString> getLocales(){
        return new ArrayList<LocaleString>(Arrays.asList(convertFromGson()));
    }

    /**
     * remove localized string
     * @param locale
     * @return
     */
    public boolean removeString (String locale){
        List <LocaleString> list = new ArrayList<LocaleString>(Arrays.asList(convertFromGson()));
        LocaleString localeString = new LocaleString(locale,"");

        boolean returnValue = list.remove(localeString);

        convertToGson(list.toArray(new LocaleString[0]));
        return returnValue;
    }

    /**
     * Add/Update localized string
     * @param locale
     * @param text
     */
    public void addString (String locale, String text){
        List <LocaleString> list = new ArrayList<LocaleString>(Arrays.asList(convertFromGson()));
        LocaleString localeString = new LocaleString(locale,text);

        list.remove(localeString);
        list.add(localeString);

        convertToGson(list.toArray(new LocaleString[0]));
    }

    private LocaleString[] convertFromGson(){
        return getGson().fromJson(encodedMultiLanguageString, LocaleString[].class);
    }

    private void convertToGson(LocaleString[] localeStrings){
        encodedMultiLanguageString = getGson().toJson(localeStrings);
    }

    private Gson getGson() {
        if (gson==null){
            gson = new GsonBuilder().create();
        }
        return gson;
    }

    private void setGson(Gson gson) {
        this.gson = gson;
    }

    /**
     * This is the Json Value of the string. Do not use it at least you are not sure on what you are doing.
     * To get data use method getString.
     * To add data use method addString.
     * To remove data use method removeString.
     * @return Json string
     */
    public String getEncodedMultiLanguageString() {
        return encodedMultiLanguageString;
    }

    /**
     * This is the Json Value of the string. Do not use it at least you are not sure on what you are doing.
     * To get data use method getString.
     * To add data use method addString.
     * To remove data use method removeLocale.
     * @param encodedMultiLanguageString Json string
     */
    public void setEncodedMultiLanguageString(String encodedMultiLanguageString) {
        this.encodedMultiLanguageString = encodedMultiLanguageString;
    }
}
