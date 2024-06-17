/*package com.example.dearsanta.user.integration;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.models.VerificationToken;
import com.example.dearsanta.users.repositories.UserRepository;
import com.example.dearsanta.users.repositories.VerificationTokenRepository;
import com.example.dearsanta.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jakarta.mail.internet.MimeMessage;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class RegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @MockBean
    private JavaMailSender mailSender;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        tokenRepository.deleteAll();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        mockMvc.perform(post("/api/register")
                        .param("name", "John Doe")
                        .param("email", "john.doe@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk());

        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().isEnabled()).isFalse();

        ArgumentCaptor<MimeMessage> messageCaptor = ArgumentCaptor.forClass(MimeMessage.class);
        verify(mailSender, times(1)).send(messageCaptor.capture());

        MimeMessage sentMessage = messageCaptor.getValue();
        MimeMessageHelper helper = new MimeMessageHelper(sentMessage, true);
        assertThat(helper.getMimeMessage().getAllRecipients()[0].toString()).isEqualTo("john.doe@example.com");
        assertThat(helper.getMimeMessage().getSubject()).isEqualTo("Completa tu registro.");
    }

    @Test
    public void shouldConfirmUser() throws Exception {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");

        userRepository.save(user);
        VerificationToken token = new VerificationToken(user, "token");
        tokenRepository.save(token);

        mockMvc.perform(get("/api/confirm")
                        .param("token", "token"))
                .andExpect(status().isOk());

        Optional<User> confirmedUser = userRepository.findByEmail("john.doe@example.com");
        assertThat(confirmedUser).isPresent();
        assertThat(confirmedUser.get().isEnabled()).isTrue();
    }
}
*/