package org.ddelizia.vcrud.core.mail.data;

/**
 * Created with IntelliJ IDEA.
 * User: ddelizia
 * Date: 05/02/14
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class EmailDetail {

    private String from;

    private String to;

    private String replyTo;

    private String subject;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getReplyTo() {
        return replyTo;
    }

    public void setReplyTo(String replyTo) {
        this.replyTo = replyTo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
