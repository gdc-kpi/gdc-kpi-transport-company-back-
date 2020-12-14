package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.Mail;
import com.ip737.transportcompany.transportcompany.data.entities.User;

import javax.mail.MessagingException;

public interface EmailService {
    Mail createRecoverMail(User user);

    Mail createBasicRegMail(User savedUser);

    void sendMailMessage(Mail mail, String templateName) throws MessagingException;
}
