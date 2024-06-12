package com.example.dearsanta.users.services;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.UserRepository;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

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

    @Transactional
    public String registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            return "User already exists";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Codificar la contraseña
        user.setEnabled(false);
        userRepository.save(user);

        VerificationToken token = new VerificationToken(user, generateToken());
        tokenRepository.save(token);

        // Enviar correo electrónico con el token
        sendVerificationEmail(user.getEmail(), token.getToken());

        return "Registration successful";
    }

    @Transactional
    public void confirmUser(String token) {
        Optional<VerificationToken> verificationTokenOptional = tokenRepository.findByToken(token);
        VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() -> new IllegalArgumentException("Invalid token"));

        User user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean authenticate(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    private void sendVerificationEmail(String email, String token) {
        String subject = "Complete your registration";
        String confirmationUrl = "http://localhost:8080/api/confirm?token=" + token;
        String message = "To confirm your account, please click the following link: " + confirmationUrl;

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
    }
}
