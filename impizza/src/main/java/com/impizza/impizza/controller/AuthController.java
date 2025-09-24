package com.impizza.impizza.controller;


import com.impizza.impizza.dto.AuthResponse;
import com.impizza.impizza.dto.LoginDto;
import com.impizza.impizza.dto.UserDto;
import com.impizza.impizza.exception.NotFoundException;
import com.impizza.impizza.exception.ValidationException;
import com.impizza.impizza.model.User;
import com.impizza.impizza.service.AuthService;
import com.impizza.impizza.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public User register(@RequestBody @Validated UserDto userDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }
        return userService.saveUser(userDto);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Validated LoginDto loginDto, BindingResult bindingResult) throws ValidationException {
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .reduce("", (s, e) -> s + e + " ");
            throw new ValidationException(errorMessage.trim());
        }

        User user = authService.authenticate(loginDto);
        String token = authService.generateToken(user);

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }
}
