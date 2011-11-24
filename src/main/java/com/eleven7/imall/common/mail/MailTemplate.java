package com.eleven7.imall.common.mail;

import java.util.Set;


/**
 * DOCUMENT ME!
 * @version 1.0
 */
public class MailTemplate {
    private String subject; //主题
    private String body; //邮件正文
    private Set<String> to; //收件人
    private Set<String> cc; //抄送
    private Set<String> bcc; //密送
    private Set<String> attachments; //附件路径
    private String from; //发送人邮件    

    /**
     * Creates a new MailTemplate object.
     */
    public MailTemplate() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getSubject() {
        return subject;
    }

    /**
     * DOCUMENT ME!
     *
     * @param subject DOCUMENT ME!
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getBody() {
        return body;
    }

    /**
     * DOCUMENT ME!
     *
     * @param body DOCUMENT ME!
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getTo() {
        return to;
    }

    /**
     * DOCUMENT ME!
     *
     * @param to DOCUMENT ME!
     */
    public void setTo(Set<String> to) {
        this.to = to;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getCc() {
        return cc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param cc DOCUMENT ME!
     */
    public void setCc(Set<String> cc) {
        this.cc = cc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getBcc() {
        return bcc;
    }

    /**
     * DOCUMENT ME!
     *
     * @param bcc DOCUMENT ME!
     */
    public void setBcc(Set<String> bcc) {
        this.bcc = bcc;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public Set<String> getAttachments() {
        return attachments;
    }

    /**
     * DOCUMENT ME!
     *
     * @param attachments DOCUMENT ME!
     */
    public void setAttachments(Set<String> attachments) {
        this.attachments = attachments;
    }

    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public String getFrom() {
        return from;
    }

    /**
     * DOCUMENT ME!
     *
     * @param from DOCUMENT ME!
     */
    public void setFrom(String from) {
        this.from = from;
    }
}
