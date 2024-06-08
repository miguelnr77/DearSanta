package com.example.dearsanta.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

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
        Anchor homeLink = new Anchor("", "Home");
        Anchor aboutLink = new Anchor("#", "About");
        Anchor contactLink = new Anchor("#", "Contact");
        navBar.add(homeLink, aboutLink, contactLink);

        // Footer
        footer = new Div();
        footer.setText("© 2024 DearSanta. All rights reserved.");
        footer.addClassName("footer");

        // Main Content
        mainContent = new Div();
        mainContent.addClassName("main-content");

        welcomeText = new H1("Welcome to DearSanta!");
        welcomeText.addClassName("welcome-text");
        descriptionText = new Paragraph("Manage your gift lists easily and efficiently.");
        descriptionText.addClassName("description-text");

        registerButton = new Button("Register", e ->
                getUI().ifPresent(ui -> ui.navigate("register"))
        );
        loginButton = new Button("Login", e ->
                getUI().ifPresent(ui -> ui.navigate("login"))
        );

        registerButton.addClassName("main-button");
        loginButton.addClassName("main-button");

        HorizontalLayout buttonsLayout = new HorizontalLayout(registerButton, loginButton);
        buttonsLayout.setSpacing(true);

        // Adding a Christmas tree image
        Image christmasTree = new Image("images/christmas-tree.png", "Christmas Tree");
        christmasTree.addClassName("christmas-tree");

        mainContent.add(welcomeText, descriptionText, buttonsLayout, christmasTree);

        add(header, navBar, mainContent, footer);
    }
}

