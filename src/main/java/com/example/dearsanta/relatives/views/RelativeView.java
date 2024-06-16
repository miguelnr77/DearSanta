package com.example.dearsanta.relatives.views;

import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.example.dearsanta.relatives.service.RelativeService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@PageTitle("Mantenimiento de Allegados")
@Route("relatives")
@CssImport("./styles/styles.css")
public class RelativeView extends VerticalLayout {

    private final RelativeService relativeService;
    private final AuthService authService;

    private final Grid<Relative> grid = new Grid<>(Relative.class);

    @Autowired
    public RelativeView(RelativeService relativeService, AuthService authService) {
        this.relativeService = relativeService;
        this.authService = authService;

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        configureLayout();
        configureGrid();
    }

    @PostConstruct
    private void init() {
        updateGrid();
    }

    private void configureLayout() {
        H1 title = new H1("Mantenimiento de Allegados");
        title.addClassName("view-title");

        Button addButton = new Button("Añadir Allegado", e -> openRelativeDialog(new Relative()));
        Button updateButton = new Button("Modificar Allegado", e -> {
            Relative selectedRelative = getSelectedRelative();
            if (selectedRelative != null) {
                openRelativeDialog(selectedRelative);
            }
        });
        Button deleteButton = new Button("Eliminar Allegados", e -> deleteSelectedRelatives());
        Button backButton = new Button("Volver", e -> getUI().ifPresent(ui -> ui.navigate("menu-usuario")));

        addButton.addClassName("red-button");
        updateButton.addClassName("red-button");
        deleteButton.addClassName("red-button");
        backButton.addClassName("red-button");

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, updateButton, deleteButton, backButton);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();

        add(title, buttonLayout);
    }

    private void configureGrid() {
        grid.addClassName("relative-grid");
        grid.setSizeFull();
        grid.setColumns("name");

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

        grid.setSelectionMode(SelectionMode.MULTI);
        add(grid);
    }

    private void openRelativeDialog(Relative relative) {
        Dialog dialog = new Dialog();

        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Nombre");

        if (relative.getId() != null) {
            nameField.setValue(relative.getName());
        }

        Button saveButton = new Button("Guardar", event -> {
            if (nameField.isEmpty()) {
                Notification.show("Nombre es obligatorio.");
                return;
            }

            User user = authService.getAuthenticatedUser(getCurrentRequest());
            if (user != null) {
                relative.setName(nameField.getValue());
                relative.setUserId(user.getId());

                relativeService.saveRelative(relative);
                updateGrid();
                dialog.close();
                Notification.show("Allegado " + (relative.getId() == null ? "añadido" : "actualizado") + " con éxito.");
            }
        });

        formLayout.add(nameField, saveButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private Relative getSelectedRelative() {
        Set<Relative> selectedRelatives = grid.getSelectedItems();
        if (selectedRelatives.size() == 1) {
            return selectedRelatives.iterator().next();
        } else {
            Notification.show("Debe seleccionar un solo allegado para actualizar");
            return null;
        }
    }

    private void deleteSelectedRelatives() {
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
        HttpServletRequest request = getCurrentRequest();
        User user = authService.getAuthenticatedUser(request);
        if (user != null) {
            List<Relative> relatives = relativeService.getRelativesByUserId(user.getId());
            grid.setItems(relatives);
        }
    }

    private HttpServletRequest getCurrentRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}