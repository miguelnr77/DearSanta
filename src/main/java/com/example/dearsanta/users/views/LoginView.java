package com.example.dearsanta.users.views;

import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@PageTitle("Iniciar Sesión")
@Route("login")
@CssImport("./styles/styles.css")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

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

        // Navigation Bar
        HorizontalLayout navBar = new HorizontalLayout();
        navBar.addClassName("nav-bar");
        Anchor homeLink = new Anchor("", "Inicio");
        Anchor aboutLink = new Anchor("about", "Acerca De");
        Anchor contactLink = new Anchor("contact", "Contacto");
        navBar.add(homeLink, aboutLink, contactLink);

        // Login Form
        H1 loginHeader = new H1("Iniciar Sesión");
        loginHeader.addClassName("welcome-text");

        TextField emailField = new TextField("Email");
        PasswordField passwordField = new PasswordField("Contraseña");

        Div errorMessage = new Div();
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

        // Register link
        Anchor registerLink = new Anchor("register", "¿No tienes una cuenta? Regístrate!");
        registerLink.addClassName("register-link");

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. Todos los derechos reservados.");
        footer.addClassName("footer");

        add(header, navBar, loginHeader, emailField, passwordField, loginButton, registerLink, errorMessage, footer);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        List<String> logoutParam = event.getLocation().getQueryParameters().getParameters().get("logout");
        if (logoutParam != null && !logoutParam.isEmpty()) {
            Notification.show("Has cerrado la sesión.", 3000, Notification.Position.TOP_CENTER);
        }
    }
}
