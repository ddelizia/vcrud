package org.ddelizia.vcrud.core.service.language.impl;

import org.ddelizia.vcrud.core.service.model.ModelService;
import org.ddelizia.vcrud.core.service.language.TranslationService;
import org.ddelizia.vcrud.model.language.Translation;
import org.ddelizia.vcrud.model.language.Translation_;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 28/06/13
 * Time: 10:30
 * To change this template use File | Settings | File Templates.
 */
public class TranslationServiceImpl implements TranslationService{

    @Autowired
    private ModelService modelService;

    @Override
    public String getTranslationForKey(String key, Locale locale) {
        Translation translation = modelService.getModel(Translation_.key.getName(), key, Translation.class);
        return translation.getTranslation().getString(locale.getISO3Language());
    }

}
