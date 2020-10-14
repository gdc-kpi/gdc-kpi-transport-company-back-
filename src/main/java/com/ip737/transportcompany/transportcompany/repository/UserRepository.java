package com.ip737.transportcompany.transportcompany.repository;

import java.util.Optional;

import com.ip737.transportcompany.transportcompany.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFullname(String username);

    Boolean existsByFullname(String username);

    Boolean existsByEmail(String email);
}
