package com.example.dearsanta.relatives.views;

import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.example.dearsanta.relatives.service.RelativeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@Route("relatives")
@CssImport("./styles/styles.css")
public class RelativeView extends VerticalLayout {

    @Autowired
    private RelativeService relativeService;

    @Autowired
    private AuthService authService;

    private Grid<Relative> grid = new Grid<>(Relative.class);
    private TextField nombreField = new TextField("Nombre");

    @Autowired
    public RelativeView(HttpServletRequest request) {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        H1 title = new H1("Mantenimiento de Allegados");
        title.addClassName("form-title");

        Button addButton = new Button("Añadir Allegado", e -> addRelative(request));
        Button updateButton = new Button("Modificar Allegado", e -> updateRelative(request));
        Button deleteButton = new Button("Eliminar Allegado", e -> deleteRelative(request));

        add(header, title, nombreField, addButton, updateButton, deleteButton, grid);
    }

    @PostConstruct
    private void init() {
        updateGrid();
    }

    private void addRelative(HttpServletRequest request) {
        User user = authService.getAuthenticatedUser(request);
        if (user != null) {
            Relative relative = new Relative();
            relative.setName(nombreField.getValue());
            relative.setUserId(user.getId());
            relativeService.addRelative(relative);
            updateGrid();
        }
    }

    private void updateRelative(HttpServletRequest request) {
        Relative selected = grid.asSingleSelect().getValue();
        if (selected != null) {
            selected.setName(nombreField.getValue());
            relativeService.updateRelative(selected);
            updateGrid();
        }
    }

    private void deleteRelative(HttpServletRequest request) {
        Relative selected = grid.asSingleSelect().getValue();
        if (selected != null) {
            relativeService.deleteRelative(selected.getId());
            updateGrid();
        }
    }

    private void updateGrid() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        User user = authService.getAuthenticatedUser(request);
        if (user != null) {
            List<Relative> relatives = relativeService.getRelativesByUserId(user.getId());
            grid.setItems(relatives);
        }
    }
}
