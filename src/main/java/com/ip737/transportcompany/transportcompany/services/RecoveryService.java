package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import com.ip737.transportcompany.transportcompany.web.dto.DtoMail;

public interface RecoveryService {
    void sendRecoveryLink(DtoMail user);

    String confirmRecovery(String recoverUrl);

    void changePassword(DtoForgotPassword passwordDto);
}
