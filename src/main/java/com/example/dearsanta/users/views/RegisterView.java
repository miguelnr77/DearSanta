package com.example.dearsanta.users.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.web.client.RestTemplate;

@Route("register")
@CssImport("./styles/styles.css")
public class RegisterView extends VerticalLayout {

    private final RestTemplate restTemplate = new RestTemplate();

    public RegisterView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.addClassName("main-content");

        Div registerForm = new Div();
        registerForm.addClassName("register-form");

        H1 title = new H1("Register");
        title.addClassName("form-title");
        TextField nameField = new TextField("Name");
        nameField.addClassName("form-field");
        TextField emailField = new TextField("Email");
        emailField.addClassName("form-field");
        PasswordField passwordField = new PasswordField("Password");
        passwordField.addClassName("form-field");
        Button registerButton = new Button("Register");
        registerButton.addClassName("register-button");

        registerButton.addClickListener(e -> {
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
        });

        registerForm.add(title, nameField, emailField, passwordField, registerButton);
        mainContent.add(registerForm);

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. All rights reserved.");
        footer.addClassName("footer");

        add(header, mainContent, footer);
    }
}
