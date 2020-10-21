package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.web.dto.DtoForgotPassword;
import com.ip737.transportcompany.transportcompany.web.dto.DtoMail;

public interface RecoveryService {
    void sendRecoveringUrl(DtoMail user);

    String confirmRecovery(String recoverUrl);

    void changePassword(DtoForgotPassword passwordDto);
}
