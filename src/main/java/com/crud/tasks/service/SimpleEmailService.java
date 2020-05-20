package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    @Autowired
    private JavaMailSender javaMailSender;//jest interfejsem, jednakże implementuje go klasa JavaMailSenderImpl, która posiada już wszystkie niezbędne implementacje metod,

    public void send(final Mail mail) {
        LOGGER.info("Starting email preparation");
        try {
            javaMailSender.send(createMailMessage(mail));
            LOGGER.info("Mail has been sent.");
        } catch (MailException e) {
           LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    public static SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        if(mail.getToCc().isEmpty()) {
            mailMessage.setTo(mail.getMailTo());
            System.out.println("Email has been sent without an additional recipient");
            return mailMessage;
        }
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setCc(mail.getToCc());
        System.out.println("Email has been sent with an additional recipient");
        return mailMessage;
    }
}


