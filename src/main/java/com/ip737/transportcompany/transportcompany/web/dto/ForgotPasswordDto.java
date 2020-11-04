package com.ip737.transportcompany.transportcompany.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordDto {

    private String recoveryLink;

    private String password;
}
