package com.example.dearsanta.Views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;

@Route("")
@CssImport("./styles/styles.css")
public class MainView extends VerticalLayout {

    private H1 welcomeText;
    private Paragraph descriptionText;
    private Button registerButton;
    private Button loginButton;
    private Div header;
    private Div footer;
    private Div mainContent;
    private HorizontalLayout navBar;

    public MainView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        // Header
        header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Navigation Bar
        navBar = new HorizontalLayout();
        navBar.addClassName("nav-bar");
        Anchor homeLink = new Anchor("", "Inicio");
        Anchor aboutLink = new Anchor("about", "Acerca De");
        Anchor contactLink = new Anchor("contact", "Contacto");
        navBar.add(homeLink, aboutLink, contactLink);

        // Footer
        footer = new Div();
        footer.setText("© 2024 DearSanta. ");
        footer.addClassName("footer");

        // Main Content
        mainContent = new Div();
        mainContent.addClassName("main-content");

        welcomeText = new H1("BIENVENIDO A DEARSANTA!");
        welcomeText.addClassName("welcome-text");
        descriptionText = new Paragraph("¡Crea, modifica y organiza de la mejor forma tus listas de regalos!");
        descriptionText.addClassName("description-text");

        registerButton = new Button("Registrarse", e ->
                getUI().ifPresent(ui -> ui.navigate("register"))
        );
        registerButton.addClassName("main-button");

        loginButton = new Button("Iniciar sesión", e ->
                getUI().ifPresent(ui -> ui.navigate("login"))
        );
        loginButton.addClassName("main-button");

        HorizontalLayout buttonsLayout = new HorizontalLayout(registerButton, loginButton);
        buttonsLayout.setSpacing(true);

        mainContent.add(welcomeText, descriptionText, buttonsLayout);

        add(header, navBar, mainContent, footer);
    }
}
