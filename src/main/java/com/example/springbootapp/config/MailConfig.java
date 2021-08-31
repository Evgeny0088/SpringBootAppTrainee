package com.example.springbootapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class MailConfig {

    @Value("${mail.smtp.username}")
    private String username;

    @Value("${mail.smtp.password}")
    private String password;

    @Value("${mail.smtp.port}")
    private int port;

    @Value("${mail.smtp.host}")
    private String host;

    @Value("${mail.smtp.protocol}")
    private String protocol;

    @Value("${mail.smtp.debug}")
    private String debug;

    @Value("${mail.smtp.auth}")
    private String auth;

    @Value("${mail.smtp.ssl.enable}")
    private String starttls_enable;


    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        Properties properties = mailSender.getJavaMailProperties();
        properties.setProperty("mail.smtp.protocol", protocol);
        properties.setProperty("mail.smtp.auth", auth);
        properties.setProperty("mail.smtp.ssl.enable", starttls_enable);
        properties.setProperty("mail.smtp.debug", debug);

        return mailSender;
    }

}
