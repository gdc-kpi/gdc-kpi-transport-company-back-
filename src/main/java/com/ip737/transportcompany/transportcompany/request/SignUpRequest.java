package com.ip737.transportcompany.transportcompany.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String fullName;
    private String email;
    private String role;
    private String password;
}
