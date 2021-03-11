package com.teamwork.integrationproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.File;
import java.util.Date;

/**
 * Author liucy(liucy@infoepoch.com)
 * Date 2021/3/4 16:58
 * 针对于发邮件示例
 */
@RestController
public class EmailController {

    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sendEmail(String fileName){
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setFrom("liucy@infoepoch.com");
            messageHelper.setTo("2687784802@qq.com");
            messageHelper.setSubject("咳咳！");
            messageHelper.setText("happy new Year");
            messageHelper.setSentDate(new Date());
            messageHelper.addAttachment("咳咳.xlsx", new File(fileName));
            mailSender.send(messageHelper.getMimeMessage());
            //邮件发送完后进行文件删除
            File file = new File(fileName);
            file.delete();
        } catch (MessagingException e) {
            throw new RuntimeException("邮件发送失败",e);
        }
    }
}
