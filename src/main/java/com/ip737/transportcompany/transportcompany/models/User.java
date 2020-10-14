package com.ip737.transportcompany.transportcompany.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name="user_id")
    private UUID id;

    private String fullname;
    private String email;
    private String password;
    private String role;
    private boolean isActivated;
    private String link;

    public User() {
    }

    public User(String fullname, String email, String password, String role, boolean isActivated, String link) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isActivated = isActivated;
        this.link = link;
    }
}