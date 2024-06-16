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
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
@PageTitle("Registrarse")
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

        H1 title = new H1("Registrarse");
        title.addClassName("form-title");

        TextField nameField = new TextField("Nombre");
        nameField.addClassName("form-field");
        nameField.setRequired(true);
        nameField.setErrorMessage("Nombre requerido");

        TextField emailField = new TextField("Email");
        emailField.addClassName("form-field");
        emailField.setRequired(true);
        emailField.setErrorMessage("Email requerido");

        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.addClassName("form-field");
        passwordField.setRequired(true);
        passwordField.setErrorMessage("Contraseña requerida");

        Button registerButton = new Button("Registrarse");
        registerButton.addClassName("red-button");

        registerButton.addClickListener(e -> {
            if (nameField.isEmpty() || emailField.isEmpty() || passwordField.isEmpty()) {
                Notification.show("Es necesario incluir todos los campos.", 3000, Notification.Position.MIDDLE);
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

        Anchor loginLink = new Anchor("login", "¿Ya se encuentra registrado? Inicie sesión!");

        mainContent.add(title, nameField, emailField, passwordField, registerButton, loginLink);

        add(header, navBar, mainContent, footer);
    }
}
