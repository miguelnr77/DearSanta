package com.example.dearsanta.list.views;

import com.example.dearsanta.Views.MainView;
import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.services.GiftListService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;

@PageTitle("Mis Listas")
@Route(value = "gift-lists")
@PermitAll
@RequestScope
@CssImport("./styles/styles.css")
public class GiftListView extends VerticalLayout {

    private final GiftListService giftListService;
    private final AuthService authService;
    private Grid<GiftList> grid = new Grid<>(GiftList.class);
    private User user;

    @Autowired
    public GiftListView(GiftListService giftListService, AuthService authService, HttpServletRequest request) {
        this.giftListService = giftListService;
        this.authService = authService;
        this.user = authService.getAuthenticatedUser(request);

        if (user != null) {
            addClassName("list-view");
            setSizeFull();
            configureGrid();
            configureLayout();
            updateList();
        } else {

        }
    }

    private void configureGrid() {
        grid.addClassName("gift-list-grid");
        grid.setSizeFull();
        grid.setColumns("name", "status");


        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addComponentColumn(giftList -> new Button("Editar", click -> {
            String giftListId = String.valueOf(giftList.getId());
            getUI().ifPresent(ui -> ui.navigate("edit-gift/" + giftListId));
        })).setHeader("Acciones");
    }

    private void configureLayout() {
        H1 title = new H1("Mis Listas");
        title.addClassName("list-title");

        Button addButton = new Button("Añadir lista", click -> openAddDialog());
        Button deleteButton = new Button("Eliminar selección", click -> deleteSelectedLists());
        Button backButton = new Button("Volver", click -> getUI().ifPresent(ui -> ui.navigate("menu-usuario")));

        addButton.addClassName("red-button");
        deleteButton.addClassName("red-button");
        backButton.addClassName("red-button");

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, deleteButton, backButton);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();

        VerticalLayout layout = new VerticalLayout(title, buttonLayout, grid);
        layout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        layout.setSizeFull();

        add(layout);
    }

    private void updateList() {
        grid.setItems(giftListService.findByUser(user));
    }

    private void openAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("Lista");

        Button saveButton = new Button("Guardar", event -> {
            GiftList giftList = new GiftList();
            giftList.setName(nameField.getValue());
            giftList.setStatus(GiftList.Status.PENDIENTE);
            giftList.setUser(user);

            giftListService.saveGiftList(giftList);
            updateList();
            dialog.close();
        });

        formLayout.add(nameField, saveButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void deleteSelectedLists() {
        Set<GiftList> selectedLists = grid.getSelectedItems();
        if (!selectedLists.isEmpty()) {
            for (GiftList giftList : selectedLists) {
                giftListService.deleteGiftList(giftList.getId());
            }
            updateList();
            Notification.show("Listas seleccionadas eliminadas con exito");
        } else {
            Notification.show("Selecciona listas para eliminar");
        }
    }
}
