package com.example.dearsanta.users.views;

import com.example.dearsanta.Views.MainView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("register")
@CssImport("./styles/styles.css")
public class RegisterView extends VerticalLayout {

    public RegisterView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Navigation Bar
        HorizontalLayout navBar = new HorizontalLayout();
        navBar.addClassName("nav-bar");
        navBar.setWidthFull();
        navBar.setJustifyContentMode(JustifyContentMode.CENTER); // Center the nav bar items

        RouterLink homeLink = new RouterLink("Inicio", MainView.class);
        Anchor aboutLink = new Anchor("#", "Acerca De");
        Anchor contactLink = new Anchor("#", "Contacto");
        navBar.add(homeLink, aboutLink, contactLink);

        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.addClassName("main-content");

        Div registerForm = new Div();
        registerForm.addClassName("register-form");

        H1 title = new H1("Registrate");
        title.addClassName("form-title");
        TextField nameField = new TextField("Nombre");
        nameField.addClassName("form-field");
        TextField emailField = new TextField("Email");
        emailField.addClassName("form-field");
        PasswordField passwordField = new PasswordField("Contraseña");
        passwordField.addClassName("form-field");
        Button registerButton = new Button("Registrarse");
        registerButton.addClassName("register-button");

        registerButton.addClickListener(e -> {
            String name = nameField.getValue();
            String email = emailField.getValue();
            String password = passwordField.getValue();

            // Send a registration request to the backend
            // Handle success or error responses
        });

        registerForm.add(title, nameField, emailField, passwordField, registerButton);
        mainContent.add(registerForm);

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. ");
        footer.addClassName("footer");

        add(header, navBar, mainContent, footer);
    }
}
