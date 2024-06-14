package com.example.dearsanta.relatives.views;

import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.example.dearsanta.relatives.service.RelativeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Route("relatives")
@CssImport("./styles/relative-style.css")
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
        Button deleteButton = new Button("Eliminar Allegado", e -> deleteSelectedRelatives(request));

        VerticalLayout buttonLayout = new VerticalLayout(addButton, updateButton, deleteButton);
        buttonLayout.addClassName("relative-buttons");

        grid.setSelectionMode(SelectionMode.MULTI);
        grid.removeAllColumns();
        grid.addColumn(Relative::getName).setHeader("Nombre").setAutoWidth(true);
        grid.addComponentColumn(relative -> {
            Checkbox checkbox = new Checkbox();
            checkbox.addValueChangeListener(event -> {
                if (event.getValue()) {
                    grid.select(relative);
                } else {
                    grid.deselect(relative);
                }
            });
            return checkbox;
        }).setHeader("Seleccionar");

        Div gridContainer = new Div(grid);
        gridContainer.addClassName("relative-grid");

        Div mainContainer = new Div(buttonLayout, gridContainer);
        mainContainer.addClassName("relative-container");

        add(header, title, nombreField, mainContainer);
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
            Notification.show("Allegado añadido");
        }
    }

    private void updateRelative(HttpServletRequest request) {
        Set<Relative> selectedRelatives = grid.getSelectedItems();
        if (selectedRelatives.size() == 1) {
            Relative selected = selectedRelatives.iterator().next();
            selected.setName(nombreField.getValue());
            relativeService.updateRelative(selected);
            updateGrid();
            Notification.show("Allegado actualizado");
        } else {
            Notification.show("Debe seleccionar un solo allegado para actualizar");
        }
    }

    private void deleteSelectedRelatives(HttpServletRequest request) {
        Set<Relative> selectedRelatives = grid.getSelectedItems();
        if (!selectedRelatives.isEmpty()) {
            List<Long> idsToDelete = new ArrayList<>();
            for (Relative relative : selectedRelatives) {
                idsToDelete.add(relative.getId());
            }
            for (Long id : idsToDelete) {
                relativeService.deleteRelative(id);
            }
            updateGrid();
            Notification.show("Allegados eliminados");
        } else {
            Notification.show("Debe seleccionar al menos un allegado para eliminar");
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
