package org.ddelizia.vcrud.model.language;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.ddelizia.vcrud.model.utils.LocaleString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.Arrays;
import java.util.List;

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

    private String encodedMultiLanguageString;

    public void addText (String locale, String text){

    }

    /**
     * Get localized String
     * @param locale
     * @return
     */
    public String getString (String locale){
        LocaleString[] localeStrings = convertFromGson();

        for(LocaleString localeString : localeStrings){
            if (localeString.getLanguage().equals(locale)){
                return localeString.getText();
            }
        }
        return null;
    }

    /**
     * remove localized string
     * @param locale
     * @return
     */
    public boolean removeString (String locale){
        List <LocaleString> list = Arrays.asList(convertFromGson());

        for(int i=0; i<list.size(); i++){
            if (list.get(i).getLanguage().equals(locale)){
                list.remove(i);
                convertToGson(list.toArray(new LocaleString[0]));
                return true;
            }
        }
        return false;
    }

    /**
     * Add/Update localized string
     * @param locale
     * @param text
     */
    private void addString (String locale, String text){
        List <LocaleString> list = Arrays.asList(convertFromGson());
        for (LocaleString localeString: list){
            if (localeString.getLanguage().equals(locale)){
                localeString.setText(text);
                convertToGson(list.toArray(new LocaleString[0]));
                return;
            }
        }
        list.add(new LocaleString(locale,text));
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
