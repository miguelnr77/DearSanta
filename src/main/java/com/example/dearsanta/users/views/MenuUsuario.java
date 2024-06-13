package com.example.dearsanta.users.views;

import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

@Route("menu-usuario")
@CssImport("./styles/styles.css")
public class MenuUsuario extends VerticalLayout {

    @Autowired
    private AuthService authService;

    @Autowired
    public MenuUsuario(HttpServletRequest request) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        String userName = user != null ? user.getName() : "Usuario";

        H1 title = new H1("¡Bienvenido " + userName + "!");
        title.addClassName("welcome-text");

        Button maintenanceButton = new Button("Mantenimiento de Allegados", e ->
                getUI().ifPresent(ui -> ui.navigate("relatives"))
        );
        maintenanceButton.addClassName("main-button");

        Button logoutButton = new Button("Cerrar Sesión", e -> {
            authService.logout(request);
            getUI().ifPresent(ui -> {
                ui.navigate("login");
                ui.getPage().executeJs("window.location.reload();");
            });
        });
        logoutButton.addClassName("main-button");

        add(header, title, maintenanceButton, logoutButton);
    }
}
