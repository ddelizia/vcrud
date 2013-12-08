package org.ddelizia.vcrud.cronjob.model;

import org.ddelizia.vcrud.model.system.VcrudModel;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: danilo.delizia
 * Date: 17/09/13
 * Time: 9:44
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public class CronJob extends VcrudModel{

    @Transient
    public static final String CRONJOB_TIME_REGEX = "(((([0-9]|[0-5][0-9]),)*([0-9]|[0-5][0-9]))|(([\\*]|[0-9]|[0-5][0-9])(/|-)([0-9]|[0-5][0-9]))|([\\?])|([\\*]))[\\s](((([0-9]|[0-5][0-9]),)*([0-9]|[0-5][0-9]))|(([\\*]|[0-9]|[0-5][0-9])(/|-)([0-9]|[0-5][0-9]))|([\\?])|([\\*]))[\\s](((([0-9]|[0-1][0-9]|[2][0-3]),)*([0-9]|[0-1][0-9]|[2][0-3]))|(([\\*]|[0-9]|[0-1][0-9]|[2][0-3])(/|-)([0-9]|[0-1][0-9]|[2][0-3]))|([\\?])|([\\*]))[\\s](((([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1]),)*([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(C)?)|(([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(/|-)([1-9]|[0][1-9]|[1-2][0-9]|[3][0-1])(C)?)|(L(-[0-9])?)|(L(-[1-2][0-9])?)|(L(-[3][0-1])?)|(LW)|([1-9]W)|([1-3][0-9]W)|([\\?])|([\\*]))[\\s](((([1-9]|0[1-9]|1[0-2]),)*([1-9]|0[1-9]|1[0-2]))|(([1-9]|0[1-9]|1[0-2])(/|-)([1-9]|0[1-9]|1[0-2]))|(((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC),)*(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))|((JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(-|/)(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))|([\\?])|([\\*]))[\\s]((([1-7],)*([1-7]))|([1-7](/|-)([1-7]))|(((MON|TUE|WED|THU|FRI|SAT|SUN),)*(MON|TUE|WED|THU|FRI|SAT|SUN)(C)?)|((MON|TUE|WED|THU|FRI|SAT|SUN)(-|/)(MON|TUE|WED|THU|FRI|SAT|SUN)(C)?)|(([1-7]|(MON|TUE|WED|THU|FRI|SAT|SUN))?(L|LW)?)|(([1-7]|MON|TUE|WED|THU|FRI|SAT|SUN)#([1-7])?)|([\\?])|([\\*]))([\\s]?(([\\*])?|(19[7-9][0-9])|(20[0-9][0-9]))?| (((19[7-9][0-9])|(20[0-9][0-9]))(-|/)((19[7-9][0-9])|(20[0-9][0-9])))?| ((((19[7-9][0-9])|(20[0-9][0-9])),)*((19[7-9][0-9])|(20[0-9][0-9])))?)";

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "group", nullable = false)
    private String group;

    @Pattern(regexp = CRONJOB_TIME_REGEX)
    @Column(name = "scheduler", nullable = false)
    private String scheduler;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cronJob")
    private Job job;

    public static String getCronjobTimeRegex() {
        return CRONJOB_TIME_REGEX;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getScheduler() {
        return scheduler;
    }

    public void setScheduler(String scheduler) {
        this.scheduler = scheduler;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
