package com.Lhan.personal_blog.service.mail;

import java.util.Map;

public interface MailService {

    /**
     *  发送文本邮件
     */
    void sendSimpleMail(String to,String subject,String content);

    /**
     * 发生html邮件
     */
    void sendHtmlMail(String to,String subject,String content);

    /**
     * 发送带附件的邮件
     */
    void sendAttachmentsMail(String to,String subject,String content,String filePath);


    /**
     * 发送Thymeleaf模板邮件
     */
    void sendThymeleafMail(Map<String,Object> valueMap);
}
