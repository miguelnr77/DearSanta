package com.example.dearsanta.user.unit;

import com.example.dearsanta.users.controllers.RegisterController;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class RegisterControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegisterController registerController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    public void shouldRegisterUser() throws Exception {
        mockMvc.perform(post("/api/register")
                        .param("name", "John Doe")
                        .param("email", "john.doe@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk());

        verify(userService).registerUser(any(User.class));
    }

    @Test
    public void shouldConfirmUser() throws Exception {
        mockMvc.perform(get("/api/confirm")
                        .param("token", "123456"))
                .andExpect(status().isOk());

        verify(userService).confirmUser(anyString());
    }
}
