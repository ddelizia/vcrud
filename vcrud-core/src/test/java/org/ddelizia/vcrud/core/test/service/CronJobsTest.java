package org.ddelizia.vcrud.core.test.service;

import org.apache.log4j.Logger;
import org.ddelizia.vcrud.core.service.cronjob.CronJobService;
import org.ddelizia.vcrud.core.test.AbstractJunit4Vcrud;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 8/17/13
 * Time: 10:38 AM
 * To change this template use File | Settings | File Templates.
 */

public class CronJobsTest extends AbstractJunit4Vcrud {

    private static final Logger LOGGER = Logger.getLogger(CronJobsTest.class);

    @Autowired
    private CronJobService cronJobService;

    @Test
    public void testImportFileInsert() {
        LOGGER.info("Installing Cronjob ");

        cronJobService.installCronJob();

        cronJobService.getRunningCronJobs();

        LOGGER.info("End ");
    }



    @Override
    public void vcrudBefore() {

    }
}
