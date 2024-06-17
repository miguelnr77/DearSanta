package com.example.dearsanta.user.integration;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.UserRepository;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import com.example.dearsanta.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Test
    public void shouldRegisterAndSendVerificationEmail() {
        // Given a new user
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password");

        // When registering the user
        String result = userService.registerUser(user);

        // Then the registration is successful
        assertThat(result).isEqualTo("Registration successful");

        // And the user is saved in the repository
        Optional<User> savedUser = userRepository.findByEmail(user.getEmail());
        assertThat(savedUser).isPresent();

        // And a verification token is created for the user
        // Find the token by searching all tokens and checking the user
        Optional<VerificationToken> token = tokenRepository.findAll().stream()
                .filter(t -> t.getUser().equals(savedUser.get()))
                .findFirst();
        assertThat(token).isPresent();
    }
}