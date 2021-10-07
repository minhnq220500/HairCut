package com.example.haircut.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

public class Email {

     public void sendEmail(String cusEmail,String code) {

        //CONFIG
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("fa20haircutapplication@gmail.com");
        mailSender.setPassword("fa20haircut");

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        mailSender.setJavaMailProperties(properties);

        //NOI DUNG EMAIL
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(cusEmail);
        msg.setSubject("Verify code from Hair cut application ");
        msg.setText("Here is your verify code: "+code);

        //SEND MAIL
        mailSender.send(msg);

    }
}
