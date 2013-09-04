package org.ddelizia.vcrud.model.patch;

import org.ddelizia.vcrud.model.VcrudModel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 9/3/13
 * Time: 8:19 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Patch extends VcrudModel{

    @Column(name = "executeOnce")
    private boolean executeOnce = false;

    @Column(name = "className", unique = true)
    private String className;

    @Column(name = "executed")
    private boolean executed = false;

    @Column(name = "lastResult")
    private boolean lastResult = false;

    public boolean isExecuteOnce() {
        return executeOnce;
    }

    public void setExecuteOnce(boolean executeOnce) {
        this.executeOnce = executeOnce;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public boolean isLastResult() {
        return lastResult;
    }

    public void setLastResult(boolean lastResult) {
        this.lastResult = lastResult;
    }
}
