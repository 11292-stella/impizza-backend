package com.impizza.impizza.repository;

import com.impizza.impizza.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);

    Optional<Object> findByEmail(@NotEmpty(message = "l'email non può essere vuota") @Email(message = "formato email non valido") String email);
}
