package com.impizza.impizza.service;

import com.impizza.impizza.dto.UserDto;
import com.impizza.impizza.enumeration.Role;
import com.impizza.impizza.exception.NotFoundException;
import com.impizza.impizza.model.User;
import com.impizza.impizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * Registra un nuovo utente con ruolo CLIENTE e invia email di benvenuto.
     */
    public User saveUser(UserDto userDto) {
        // Verifica se username o email già esistono
        if (userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username già in uso");
        }
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email già registrata");
        }

        User user = new User();
        user.setNome(userDto.getNome());
        user.setCognome(userDto.getCognome());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setRole(Role.CLIENTE); // Assegna ruolo CLIENTE

        User savedUser = userRepository.save(user);
        sendRegistrationEmail(savedUser.getEmail());
        return savedUser;
    }

    /**
     * Restituisce tutti gli utenti (solo per admin).
     */
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    /**
     * Restituisce un utente tramite ID.
     */
    public User getUser(int id) throws NotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato"));
    }

    /**
     * Restituisce un utente tramite username.
     */
    public User getUserByUsername(String username) throws NotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User con username " + username + " non trovato"));
    }

    /**
     * Aggiorna i dati di un utente (solo per admin).
     */
    public User updateUser(int id, UserDto userDto) throws NotFoundException {
        User userDaAggiornare = getUser(id);

        userDaAggiornare.setNome(userDto.getNome());
        userDaAggiornare.setCognome(userDto.getCognome());
        userDaAggiornare.setUsername(userDto.getUsername());
        userDaAggiornare.setEmail(userDto.getEmail());

        // Aggiorna la password solo se è cambiata
        if (!passwordEncoder.matches(userDto.getPassword(), userDaAggiornare.getPassword())) {
            userDaAggiornare.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }

        return userRepository.save(userDaAggiornare);
    }

    /**
     * Elimina un utente (solo per admin).
     */
    public void deleteUser(int id) throws NotFoundException {
        User userDaCancellare = getUser(id);
        userRepository.delete(userDaCancellare);
    }

    /**
     * Invia email di benvenuto dopo la registrazione.
     */
    private void sendRegistrationEmail(String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Benvenuto!");
        message.setText("Congratulazioni! La tua registrazione al servizio è avvenuta con successo.\n" +
                "Ora puoi accedere e utilizzare tutte le nostre funzionalità.\n\n" +
                "Cordiali saluti!");

        try {
            javaMailSender.send(message);
            System.out.println("Email di registrazione inviata a: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Errore durante l'invio dell'email di registrazione a " + recipientEmail + ": " + e.getMessage());
        }
    }














}
