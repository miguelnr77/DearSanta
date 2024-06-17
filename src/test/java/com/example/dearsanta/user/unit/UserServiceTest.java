package com.example.dearsanta.user.unit;

import com.example.dearsanta.users.services.UserService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.UserRepository;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import jakarta.mail.MessagingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private VerificationTokenRepository tokenRepository;

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldNotRegisterExistingUser() {
        User testUser = new User();
        testUser.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(testUser));

        String result = userService.registerUser(testUser);

        assertThat(result).isEqualTo("User already exists");
    }

    @Test
    public void shouldRegisterNewUser() {
        User testUser = new User();
        testUser.setEmail("test@example.com");

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        String result = userService.registerUser(testUser);

        assertThat(result).isEqualTo("Registration successful");
        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    public void shouldConfirmUser() {
        User testUser = new User();
        testUser.setEmail("test@example.com");
        VerificationToken token = new VerificationToken(testUser, UUID.randomUUID().toString());

        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        userService.confirmUser(token.getToken());

        assertThat(testUser.isEnabled()).isTrue();
        verify(userRepository, times(1)).save(testUser);
    }
}
