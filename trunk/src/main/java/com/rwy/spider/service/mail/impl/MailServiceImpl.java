package com.rwy.spider.service.mail.impl;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luocj on 2014/11/4.
 */
@Service("mailService")
public class MailServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private VelocityEngine velocityEngine;//spring配置中定义
    @Autowired
    private JavaMailSender javaMailSender;
    private MimeMessageHelper helper;

    public void execute(String[] senTo,Map map) throws MessagingException,IOException{
        MimeMessage mime = javaMailSender.createMimeMessage();
        try {
            //建立邮件消息,
            helper = new MimeMessageHelper(mime, true, "utf-8");
            //设置寄件人
            helper.setFrom("luocj@zmyou.com");
            //设置收件人
            helper.setTo(senTo);
            //设置主题
            helper.setSubject("网络警察测试邮件");

            //通过Map传递动态参数
            Map templateMap = new HashMap();
            templateMap.put("result",map);
            String htmlText= VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, "/template/mail.vm", "UTF-8", templateMap);
            //true 表示启动HTML格式的邮件
            helper.setText(htmlText,true);
        } catch (MessagingException me) {
            me.printStackTrace();
        }
        //发送邮件
        javaMailSender.send(mime);

        logger.info("邮件发送成功..");
    }

}
