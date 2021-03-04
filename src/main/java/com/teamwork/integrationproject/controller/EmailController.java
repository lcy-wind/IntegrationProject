package com.teamwork.integrationproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
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

    public void sendEmail(){
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);//true表示支持复杂类型
            messageHelper.setFrom("liucy@infoepoch.com");//邮件发信人
            messageHelper.setTo("2687784802@qq.com");//邮件收信人
            messageHelper.setSubject("桥边姑娘");//邮件主题
            messageHelper.setText("happy new Year");//邮件内容
            messageHelper.setSentDate(new Date());
            mailSender.send(messageHelper.getMimeMessage());//正式发送邮件


//            messageHelper.setCc("happly new Year");//抄送
//            if (mailVo.getMultipartFiles() != null) {//添加邮件附件
//                for (MultipartFile multipartFile : mailVo.getMultipartFiles()) {
//                    messageHelper.addAttachment(multipartFile.getOriginalFilename(), multipartFile);
//                }
//            }

        } catch (MessagingException e) {
            throw new RuntimeException("邮件发送失败",e);
        }
    }
}
