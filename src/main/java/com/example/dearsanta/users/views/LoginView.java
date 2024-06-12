package com.example.dearsanta.users.views;

import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;

@Route("login")
@CssImport("./styles/styles.css")
public class LoginView extends VerticalLayout {

    private final AuthService authService;
    private final HttpServletRequest request;

    @Autowired
    public LoginView(AuthService authService, HttpServletRequest request) {
        this.authService = authService;
        this.request = request;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        getStyle().set("justify-content", "center");

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Login Form
        H1 loginHeader = new H1("Iniciar Sesión");
        loginHeader.addClassName("welcome-text");

        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Contraseña");

        Paragraph errorMessage = new Paragraph();
        errorMessage.setVisible(false);

        Button loginButton = new Button("Iniciar Sesión", e -> {
            boolean success = authService.login(emailField.getValue(), passwordField.getValue(), request);
            if (success) {
                getUI().ifPresent(ui -> ui.navigate("menu-usuario"));
            } else {
                errorMessage.setText("Credenciales incorrectas o cuenta no confirmada. Por favor, inténtelo de nuevo.");
                errorMessage.setVisible(true);
            }
        });
        loginButton.addClassName("main-button");

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. Todos los derechos reservados.");
        footer.addClassName("footer");

        add(header, loginHeader, emailField, passwordField, loginButton, errorMessage, footer);
    }
}
