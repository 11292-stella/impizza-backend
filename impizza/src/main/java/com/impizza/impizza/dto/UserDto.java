package com.impizza.impizza.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UserDto {

    @NotEmpty(message = "il nome non può essere vuoto")
    private String nome;

    @NotEmpty(message = "il cognome non può essere vuoto")
    private String cognome;

    @NotEmpty(message = "L'username non può essere vuoto")
    private String username;

    @NotEmpty(message = "la password non può essere vuota")
    private String password;

    @NotEmpty(message = "l'email non può essere vuota")
    @Email(message = "formato email non valido")
    private String email;


}
