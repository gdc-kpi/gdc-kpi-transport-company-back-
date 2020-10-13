package com.ip737.transportcompany.transportcompany.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String id;
    private String username;
    private String email;
    private String role;
    private boolean isActivated;
    private String link;

    public JwtResponse(String accessToken, String id, String username, String email, String role,
                       boolean isActivated, String link) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.isActivated = isActivated;
        this.link = link;
    }
}
