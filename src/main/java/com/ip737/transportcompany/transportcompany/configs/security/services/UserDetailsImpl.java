package com.ip737.transportcompany.transportcompany.configs.security.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ip737.transportcompany.transportcompany.data.entities.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@ToString
@Setter
@Getter
@EqualsAndHashCode
public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String id;
    private String fullName;
    private String email;
    @JsonIgnore
    private String password;
    private String role;
    private boolean isActivated;
    private String link;

    public UserDetailsImpl(String id, String fullName, String email, String password,
                           String role, boolean isActivated, String link) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActivated = isActivated;
        this.link = link;
    }

    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId().toString(),
                user.getFullname(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isActivated(),
                user.getLink());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.fullName;
    }


    public String getRole() {
        return this.role;
    }


    public String getId() {
        return this.id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
