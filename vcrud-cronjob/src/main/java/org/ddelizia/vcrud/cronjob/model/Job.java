package org.ddelizia.vcrud.cronjob.model;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 17/09/13
 * Time: 9:44
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class Job extends VcrudModel{

    @Column (name = "code", nullable = false, unique = true)
    private String code;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "cronJob_ref")
    private CronJob cronJob;

    @Column (name = "jobClass", nullable = false)
    private Class<? extends org.quartz.Job> jobClass;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CronJob getCronJob() {
        return cronJob;
    }

    public void setCronJob(CronJob cronJob) {
        this.cronJob = cronJob;
    }

    public Class<? extends org.quartz.Job> getJobClass() {
        return jobClass;
    }

    public void setJobClass(Class<? extends org.quartz.Job> jobClass) {
        this.jobClass = jobClass;
    }
}
