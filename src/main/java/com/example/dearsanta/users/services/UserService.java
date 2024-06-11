package com.example.dearsanta.users.services;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.UserRepository;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "User already exists with this email";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        userRepository.save(user);
        sendConfirmationEmail(user);
        return "User registered successfully. Please check your email for confirmation.";
    }

    private void sendConfirmationEmail(User user) {
        String token = UUID.randomUUID().toString();
        String confirmationUrl = "http://localhost:8080/confirm?token=" + token;
        String message = "Please confirm your registration by clicking the following link: " + confirmationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Registration Confirmation");
        email.setText(message);
        mailSender.send(email);

        VerificationToken verificationToken = new VerificationToken(token, user);
        tokenRepository.save(verificationToken);
    }

    public void enableUser(User user) {
        user.setEnabled(true);
        userRepository.save(user);
    }
}
