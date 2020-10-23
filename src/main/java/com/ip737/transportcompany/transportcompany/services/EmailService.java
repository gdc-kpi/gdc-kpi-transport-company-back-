package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import com.ip737.transportcompany.transportcompany.web.dto.Mail;

import javax.mail.MessagingException;

public interface EmailService {
    Mail createRecoverMail(User user);

    Mail createBasicRegMail(User savedUser);

    void sendMailMessage(Mail mail, String templateName) throws MessagingException;
}
