package com.ip737.transportcompany.transportcompany.response;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserLoginSuccessResponse {

    private boolean success;

    private String token;

}
