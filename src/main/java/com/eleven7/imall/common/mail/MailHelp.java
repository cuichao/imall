package com.eleven7.imall.common.mail;

import java.util.Date;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.eleven7.imall.common.CommonConfig;

/**
 * @author cuichao01
 * 此类依赖于spring 注入mailSender
 */
public class MailHelp {
    private static Logger log = LoggerFactory.getLogger("mailServiceLog");
    @Autowired
    private static JavaMailSender mailSender;

    /**
     * 发送邮件
     *
     * @param template
     */
    @Async
    public static void sendMail(MailTemplate template) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        MimeMessage mailMessage = senderImpl.createMimeMessage();

        //设置utf-8 或GBK编码，否则邮件会有乱码     
        MimeMessageHelper messageHelper;

        try {
            messageHelper = new MimeMessageHelper(mailMessage, template.getAttachments() != null, "UTF-8");

            Set<String> mailto = template.getTo();
            Set<String> ccto = template.getCc();
            Set<String> bbto = template.getBcc();

            boolean isExistTo = false;
            boolean isExistCc = false;
            boolean isExistBcc = false;

            if (mailto != null) {
                for (String to : mailto) {
                    if (StringUtils.isNotEmpty(to) && isValidAddress(to)) {
                        isExistTo = true;
                        messageHelper.addTo(to);
                    }
                }
            }

            if (ccto != null) {
                for (String cc : ccto) {
                    if (StringUtils.isNotEmpty(cc) && isValidAddress(cc)) {
                        isExistCc = true;
                        messageHelper.addCc(cc);
                    }
                }
            }

            if (bbto != null) {
                for (String bb : bbto) {
                    if (StringUtils.isNotEmpty(bb) && isValidAddress(bb)) {
                        isExistBcc = true;
                        messageHelper.addBcc(bb);
                    }
                }
            }

            if (!isExistTo && !isExistCc && !isExistBcc) {
                //记录日志
                writeLog(template, "因无接受人邮件不发送！");

                return;
            }

            String prefix = "";
            
            if (CommonConfig.isDebugMode()) {
                prefix = "测试邮件, 请忽略<br/><br/><br/>"; 
            }
            
            messageHelper.setSubject(template.getSubject());
            messageHelper.setText(prefix + template.getBody(), true);
            messageHelper.setFrom(template.getFrom());

            Set<String> attachments = template.getAttachments();

            if (attachments != null) {
                for (String attachPath : attachments) {
                    FileSystemResource file = new FileSystemResource(attachPath);
                    messageHelper.addAttachment(file.getFilename(), file);
                }
            }

            mailSender.send(mailMessage);

            //记录日志
            writeLog(template, "发送成功");
        } catch (Exception e) {
            log.error("Send mail error! ", e);

            writeLog(template, "发送失败");
        }
    }

    /**
     * 发送邮件
     *
     * @param subject
     * @param body DOCUMENT ME!
     */
    @Async
    public static void sendMail(String subject, String body,Set<String> mailto,Set<String> ccto,Set<String> bbto) {
        JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();
        MimeMessage mailMessage = senderImpl.createMimeMessage();

        //设置utf-8 或GBK编码，否则邮件会有乱码     
        MimeMessageHelper messageHelper;
        boolean isExistTo = false;
        boolean isExistCc = false;
        boolean isExistBcc = false;
      
        try {
            messageHelper = new MimeMessageHelper(mailMessage, false, "UTF-8");

            if (mailto != null) {
                for (String to : mailto) {
                    if (StringUtils.isNotEmpty(to) && isValidAddress(to)) {
                        isExistTo = true;
                        messageHelper.addTo(to);
                    }
                }
            }

            if (ccto != null) {
                for (String cc : ccto) {
                    if (StringUtils.isNotEmpty(cc) && isValidAddress(cc)) {
                        isExistCc = true;
                        messageHelper.addCc(cc);
                    }
                }
            }

            if (bbto != null) {
                for (String bb : bbto) {
                    if (StringUtils.isNotEmpty(bb) && isValidAddress(bb)) {
                        isExistBcc = true;
                        messageHelper.addBcc(bb);
                    }
                }
            }
            if (!isExistTo && !isExistCc && !isExistBcc) {
                //记录日志
                log.warn("因无接受人邮件不发送！");
                return;
            }
            String prefix = "";
            
            if (CommonConfig.isDebugMode()) {
                prefix = "测试邮件, 请忽略<br/><br/><br/>"; 
            }
            

            messageHelper.setSubject(subject);
            messageHelper.setText(prefix + body, false);
            messageHelper.setFrom(CommonConfig.get("mail.from"));
            

            mailSender.send(mailMessage);
        } catch (Exception e) {
            log.error("Send mail error! ", e);
        }
    }

    /**
     * 记录日志
     *
     * @param template
     * @param other DOCUMENT ME!
     */
    private static void writeLog(MailTemplate template, String other) {
        String timeStr = "时间：" + new Date() + "\n";
        other += "\n";

        StringBuilder mailTo = new StringBuilder();

        if ((template.getTo() != null) && !template.getTo().isEmpty()) {
            mailTo.append("收件人:");

            for (String to : template.getTo()) {
                mailTo.append(to).append(",");
            }

            mailTo.append("\n");
        }

        if ((template.getCc() != null) && !template.getCc().isEmpty()) {
            mailTo.append("抄送人:");

            for (String cc : template.getCc()) {
                mailTo.append(cc).append(",");
            }

            mailTo.append("\n");
        }

        if ((template.getBcc() != null) && !template.getBcc().isEmpty()) {
            mailTo.append("密送人:");

            for (String bcc : template.getBcc()) {
                mailTo.append(bcc).append(",");
            }

            mailTo.append("\n");
        }

        String from = "发件人:" + template.getFrom() + "\n";

        String subject = "主题:" + template.getSubject() + "\n";
        String text = "内容:" + template.getBody() + "\n\n";

        log.info(timeStr + other + mailTo.toString() + from + subject + text);
    }

    /**
     * 是否为合法的地址
     *
     * @param address
     *
     * @return
     */
    private static boolean isValidAddress(String address) {
        InternetAddress[] arrayOfInternetAddress = null;

        try {
            arrayOfInternetAddress = InternetAddress.parse(address);
        } catch (AddressException e) {
            //ignore
            return false;
        }

        if ((arrayOfInternetAddress == null) || (arrayOfInternetAddress.length != 1)) {
            return false;
        }

        return true;
    }


    /**
     * DOCUMENT ME!
     *
     * @return DOCUMENT ME!
     */
    public static JavaMailSender getMailSender() {
        return mailSender;
    }

    /**
     * DOCUMENT ME!
     *
     * @param mailSender DOCUMENT ME!
     */
    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        MailHelp.mailSender = mailSender;
    }
}
