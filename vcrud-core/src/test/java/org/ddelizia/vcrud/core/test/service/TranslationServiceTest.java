package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.ddelizia.vcrud.model.language.Translation;
import org.ddelizia.vcrud.model.language.Translation_;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/08/13
 * Time: 22:53
 * To change this template use File | Settings | File Templates.
 */
public class TranslationServiceTest extends AbstractJunit4Vcrud{

    private final static Logger LOGGER = Logger.getLogger(TranslationServiceTest.class);

    private final static String TRANSLATION_KEY1 = "theKey1";
    private final static String TRANSLATION_LANG1 = "ES";
    private final static String TRANSLATION_LANG2 = "EN";
    private final static String TRANSLATION_LANG1_VALUE = "Hola";
    private final static String TRANSLATION_LANG2_VALUE = "Hello";

    @Test
    public void testTranslation(){
        Translation translation = getModelService().getModel(Translation_.key.getName(), TRANSLATION_KEY1, Translation.class);
        Assert.assertTrue(translation!=null);
        Assert.assertEquals(translation.getTranslation().getLocales().size(),2);
        Assert.assertEquals(translation.getTranslation().getString(TRANSLATION_LANG1),TRANSLATION_LANG1_VALUE);
        Assert.assertEquals(translation.getTranslation().getString(TRANSLATION_LANG2),TRANSLATION_LANG2_VALUE);
    }

    @Test
    public void testTranslationAgain(){
        Translation translation = getModelService().getModel(Translation_.key.getName(), TRANSLATION_KEY1, Translation.class);
        Assert.assertTrue(translation!=null);
        Assert.assertEquals(translation.getTranslation().getLocales().size(),2);
        Assert.assertEquals(translation.getTranslation().getString(TRANSLATION_LANG1),TRANSLATION_LANG1_VALUE);
        Assert.assertEquals(translation.getTranslation().getString(TRANSLATION_LANG2),TRANSLATION_LANG2_VALUE);
    }

    @Override
    public void vcrudBefore() {
        LOGGER.info("Instanciating new object");
        createTranslationObjectAndStore();
    }

    private void createTranslationObjectAndStore(){
        Translation translation = getModelService().getModel(Translation_.key.getName(), TRANSLATION_KEY1, Translation.class);
        if (translation == null){
            translation = new Translation();
        }
        translation.setKey(TRANSLATION_KEY1);
        translation.addTranslation(TRANSLATION_LANG1, TRANSLATION_LANG1_VALUE);
        translation.addTranslation(TRANSLATION_LANG2, TRANSLATION_LANG2_VALUE);
        getModelService().rapidMerge(translation);
    }
}
