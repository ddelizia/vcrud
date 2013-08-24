package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.service.media.MediaService;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 21/08/13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class MediaServiceTest extends AbstractJunit4Vcrud{

    private final static Logger LOGGER = Logger.getLogger(MediaServiceTest.class);

    @Autowired
    @Qualifier("VcrudMediaService")
    private MediaService mediaService;

    @Value("${media.directory}")
    private String mediaDirectory;

    @Value("${media.datePath}")
    private String mediaDatePath;

    @Override
    public void vcrudBefore() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Test
    public void checkVariablesInstanciated(){
        Assert.assertNotNull(mediaDirectory);
        Assert.assertNotNull(mediaDatePath);
    }

    @Test
    public void storeFile(){
        LOGGER.info("storing "+mediaService.storeMedia(new byte[0],"test.txt"));
    }
}
