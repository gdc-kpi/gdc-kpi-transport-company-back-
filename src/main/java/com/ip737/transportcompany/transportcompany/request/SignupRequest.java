package com.ip737.transportcompany.transportcompany.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String username;
    private String email;
    private String role;
    private String password;
    private boolean isActivated;
    private String link;
}
