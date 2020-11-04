package com.ip737.transportcompany.transportcompany.web.dto;

import com.ip737.transportcompany.transportcompany.configs.constants.Constants;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpDto {
    private String fullname;
    private String email;
    private String password;

    public User toUser() {
        return User.builder()
                .password(this.password)
                .email(this.email)
                .role(Constants.ROLE_DRIVER)
                .fullname(this.fullname)
                .isActivated(false)
                .link("")
                .recoveryLink("")
                .build();
    }
}
