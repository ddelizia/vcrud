package org.ddelizia.vcrud.core.service.cronjob.impl;

import org.ddelizia.vcrud.core.service.cronjob.CronJobService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 17/09/13
 * Time: 9:43
 * To change this template use File | Settings | File Templates.
 */

@Service("CronJobService")
public class CronJobServiceImpl implements CronJobService{

    private SchedulerFactory sf = new StdSchedulerFactory();


    public void installCronJob(){//CronJob cronJob){
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();
        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        JobDetail job = JobBuilder.newJob(MyJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(1))
                .build();

        if (sched != null) {
            try {
                sched.scheduleJob(job, trigger);
                sched.start();
            } catch (SchedulerException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    public void getRunningCronJobs(){
        Scheduler sched = null;
        try {
            sched = sf.getScheduler();

        } catch (SchedulerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    private class MyJob implements Job{

        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            System.out.println("hola mi sto eseguendo!!!!!!!!");
        }
    }
}
