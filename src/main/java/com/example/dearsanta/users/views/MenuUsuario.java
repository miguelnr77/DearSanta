package com.example.dearsanta.users.views;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;

@Route("menu-usuario")
@CssImport("./styles/styles.css")
public class MenuUsuario extends VerticalLayout implements BeforeEnterObserver {

    private final AuthService authService;
    private final HttpServletRequest request;

    @Autowired
    public MenuUsuario(AuthService authService, HttpServletRequest request) {
        this.authService = authService;
        this.request = request;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        getStyle().set("justify-content", "center");

        User user = authService.getAuthenticatedUser(request);

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Welcome Message
        H1 welcomeText = new H1("¡Bienvenido, " + (user != null ? user.getEmail() : "") + "!");
        welcomeText.addClassName("welcome-text");

        // Options
        Button mantenimientoButton = new Button("Mantenimiento de allegados", e -> {
            // Acción para Mantenimiento de allegados
        });
        mantenimientoButton.addClassName("main-button");

        Button listasButton = new Button("Mis listas", e -> {
            // Acción para Mis listas
        });
        listasButton.addClassName("main-button");

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. Todos los derechos reservados.");
        footer.addClassName("footer");

        add(header, welcomeText, mantenimientoButton, listasButton, footer);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!authService.isAuthenticated(request)) {
            event.forwardTo("login");
        }
    }
}
