package com.Lhan.personal_blog.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Service

public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.from}")
    private String from;


    private static final String template="admin/mail";

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);

    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        //获取MimeMessage对象
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try
        {
            messageHelper = new MimeMessageHelper(message,true);
            //邮件发送人
            messageHelper.setFrom(from);
            //邮件接收人
            messageHelper.setTo(to);
            //邮件主题
            message.setSubject(subject);
            //邮件内容，html格式
            messageHelper.setText(content, true);
            //发送
            mailSender.send(message);

        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(message,true);
            messageHelper.setFrom(from);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(content,true);
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            messageHelper.addAttachment(fileName,file);
            mailSender.send(message);

        }catch (MessagingException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void sendThymeleafMail(Map<String, Object> valueMap) {
        MimeMessage mimeMessage = null;
        try {

            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);


            //设置发件人邮箱
            helper.setFrom(from);
            //设置收件人邮箱
            helper.setTo((String)valueMap.get("to"));
            //设置邮件标题
            helper.setSubject((String)valueMap.get("title"));
            //添加正文(使用thymeleaf模板)
            Context context = new Context();
            context.setVariables(valueMap);
            String content = this.templateEngine.process(template,context);
            helper.setText(content,true);

            //添加附件
            if (valueMap.get("filePathList")!= null)
            {
                String[] filePathList = (String[]) valueMap.get("filePathList");
                for (String filePath : filePathList)
                {
                    FileSystemResource fileSystemResource = new FileSystemResource(new File(filePath));
                    String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                    helper.addAttachment(fileName,fileSystemResource);
                }
            }

            //添加封面和logo
//            ClassPathResource logoImage = new ClassPathResource((String)valueMap.get("logoImage"));
//            ClassPathResource coverImage = new ClassPathResource((String)valueMap.get("coverImage"));

//            helper.addInline("logoImage",logoImage);
//            helper.addInline("coverImage",coverImage);

            //发送邮件
            mailSender.send(mimeMessage);
        }
        catch (MessagingException e)
        {
            e.printStackTrace();
        }

    }


}
