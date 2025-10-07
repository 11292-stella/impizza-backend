package com.impizza.impizza.controller;

import com.impizza.impizza.dto.UserDto;
import com.impizza.impizza.exception.NotFoundException;
import com.impizza.impizza.exception.ValidationException;
import com.impizza.impizza.model.User;
import com.impizza.impizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class UserController {

    @Autowired
    private UserService userService;

    // Solo l'admin può vedere tutti gli utenti registrati
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUser();
    }

    // Solo l'admin può vedere i dettagli di un singolo utente
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User getUserById(@PathVariable int id) throws NotFoundException {
        return userService.getUser(id);
    }

    // Solo l'admin può aggiornare un utente (es. correzione dati)
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public User updateUser(@PathVariable int id,
                           @RequestBody @Validated UserDto userDto,
                           BindingResult bindingResult) throws NotFoundException, ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }

        return userService.updateUser(id, userDto);
    }

    // Solo l'admin può eliminare un utente
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable int id) throws NotFoundException {
        userService.deleteUser(id);
        return "User with ID " + id + " deleted successfully";
    }
}

