package com.example.dearsanta.users.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Cerrar Sesión")
@Route("logout")
@CssImport("./styles/styles.css")
public class LogoutView extends VerticalLayout {

    public LogoutView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();
        getStyle().set("justify-content", "center");

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Logout Message
        H1 logoutHeader = new H1("Has cerrado la sesión");
        logoutHeader.addClassName("logout-message");

        // Redirect Button
        Button homeButton = new Button("Ir a la página de inicio", e -> {
            getUI().ifPresent(ui -> ui.navigate(""));
        });
        homeButton.addClassName("main-button");

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. Todos los derechos reservados.");
        footer.addClassName("footer");

        add(header, logoutHeader, homeButton, footer);

        // Mostrar notificación de cierre de sesión
        Notification.show("Has cerrado la sesión.", 3000, Notification.Position.TOP_CENTER);
    }
}
