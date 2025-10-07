package com.impizza.impizza.service;


import com.impizza.impizza.dto.LoginDto;
import com.impizza.impizza.exception.NotFoundException;
import com.impizza.impizza.exception.ValidationException;
import com.impizza.impizza.model.User;
import com.impizza.impizza.repository.UserRepository;
import com.impizza.impizza.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User authenticate(LoginDto loginDto) throws ValidationException {
        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ValidationException("Username non trovato"));

        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ValidationException("Password errata");
        }

        return user;
    }

    public String generateToken(User user) {
        return jwtTool.generateToken(user); // ✅ metodo corretto
    }
}