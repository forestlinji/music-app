package com.forestj.service.impl;

import com.forestj.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 真正的邮件发送类
 */
@Service
@Slf4j
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;


    @Override
    @Async
    public void SendActiveMail(String email, String token) {
        //设置发送html格式邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(username));
            helper.setTo(email);
            helper.setSubject("music账户激活邮件");
            StringBuilder builder = new StringBuilder();
            builder.append("<!DOCTYPE html>\n")
                    .append("<html lang=\"en\">\n")
                    .append("<head>\n")
                    .append("    <meta charset=\"UTF-8\">\n")
                    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                    .append("    <title>激活邮件</title>\n")
                    .append("</head>\n")
                    .append("<body>\n")
                    .append("亲爱的用户:\n")
                    .append("<br/>\n")
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;欢迎您使用music服务，这是一封由系统发送的激活邮件，请勿回复。请输入以下激活码进行激活，否则，请忽略该邮件\n")
                    .append("<br/>\n")
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;<p>您的激活码是: " + token + ", 该激活码10分钟内有效" + "</p>\n")
                    .append("</body>\n")
                    .append("</html>");
            helper.setText(builder.toString(), true);
            log.info(email + "发送激活邮件");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void SendForgetMail(String email, String changeToken) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(username));
            helper.setTo(email);
            helper.setSubject("music重置密码邮件");
            StringBuilder builder = new StringBuilder();
            builder.append("<!DOCTYPE html>\n")
                    .append("<html lang=\"en\">\n")
                    .append("<head>\n")
                    .append("    <meta charset=\"UTF-8\">\n")
                    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
                    .append("    <title>重置密码邮件</title>\n")
                    .append("</head>\n")
                    .append("<body>\n")
                    .append("亲爱的用户:\n")
                    .append("<br/>\n")
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;欢迎您使用music服务，这是一封由系统发送的重置密码邮件，请勿回复。请输入以下激活码进行密码重置，否则，请忽略该邮件\n")
                    .append("<br/>\n")
                    .append("&nbsp;&nbsp;&nbsp;&nbsp;<p>您的激活码是: " + changeToken + ", 该激活码10分钟内有效" + "</p>\n")
                    .append("</body>\n")
                    .append("</html>");

            helper.setText(builder.toString(), true);
            log.info(email + "发送重置密码邮件");
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


}
