package org.ddelizia.vcrud.cronjob.service;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 17/09/13
 * Time: 9:43
 * To change this template use File | Settings | File Templates.
 */
public interface CronJobService {


    public void installCronJob();

    public void getRunningCronJobs();
}
