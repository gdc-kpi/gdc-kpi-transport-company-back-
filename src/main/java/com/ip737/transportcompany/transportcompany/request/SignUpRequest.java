package com.ip737.transportcompany.transportcompany.request;

import com.ip737.transportcompany.transportcompany.data.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {
    private String fullname;
    private String email;
    private String role;
    private String password;

    public User toUser(){
        return User.builder()
                .password(this.password)
                .email(this.email)
                .role(this.role)
                .fullname(this.fullname)
                .isActivated(false)
                .link("")
                .build();
    }
}
