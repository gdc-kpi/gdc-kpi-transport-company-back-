package com.ip737.transportcompany.transportcompany.services;

import com.ip737.transportcompany.transportcompany.web.dto.ForgotPasswordDto;

public interface RecoveryService {
    void sendRecoveryLink(String password);

    void confirmRecovery(String recoverUrl);

    void changePassword(ForgotPasswordDto passwordDto);
}
