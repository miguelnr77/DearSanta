package com.example.dearsanta.users.views;

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
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

@Route("/register")
@CssImport("./styles/styles.css")
public class RegisterView extends VerticalLayout {

    private final RestTemplate restTemplate;

    @Autowired
    public RegisterView(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

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

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. ");
        footer.addClassName("footer");

        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.addClassName("main-content");

        H1 title = new H1("Register");
        title.addClassName("form-title");

        TextField nameField = new TextField("Name");
        nameField.addClassName("form-field");
        nameField.setRequired(true);
        nameField.setErrorMessage("Name is required");

        TextField emailField = new TextField("Email");
        emailField.addClassName("form-field");
        emailField.setRequired(true);
        emailField.setErrorMessage("Email is required");

        PasswordField passwordField = new PasswordField("Password");
        passwordField.addClassName("form-field");
        passwordField.setRequired(true);
        passwordField.setErrorMessage("Password is required");

        Button registerButton = new Button("Register");
        registerButton.addClassName("register-button");

        registerButton.addClickListener(e -> {
            if (nameField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty()) {
                Notification.show("All fields are required", 3000, Notification.Position.MIDDLE);
            } else {
                String name = nameField.getValue();
                String email = emailField.getValue();
                String password = passwordField.getValue();

                // Send a registration request to the backend
                String url = "http://localhost:8080/api/register?name=" + name + "&email=" + email + "&password=" + password;
                try {
                    String response = restTemplate.postForObject(url, null, String.class);
                    Notification.show(response);
                } catch (Exception ex) {
                    Notification.show("Error: " + ex.getMessage());
                }
            }
        });

        mainContent.add(title, nameField, emailField, passwordField, registerButton);

        add(header, navBar, mainContent, footer);
    }
}
