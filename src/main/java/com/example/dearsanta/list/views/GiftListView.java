package com.example.dearsanta.list.views;

import com.example.dearsanta.Views.MainView;
import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.services.GiftListService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridMultiSelectionModel;
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

@PageTitle("Gift Lists")
@Route(value = "gift-lists")
@PermitAll
@RequestScope
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
            configureButtons();
            updateList();
        } else {
            // Handle unauthenticated user case
        }
    }

    private void configureGrid() {
        grid.addClassName("gift-list-grid");
        grid.setSizeFull();
        grid.setColumns("name", "status");

        // Añadir una columna de checkboxes para selección múltiple
        grid.setSelectionMode(Grid.SelectionMode.MULTI);

        grid.addComponentColumn(giftList -> new Button("Edit", click -> {
            String giftListId = String.valueOf(giftList.getId());
            getUI().ifPresent(ui -> ui.navigate("edit-gift/" + giftListId));
        })).setHeader("Actions");

        add(grid);
    }

    private void configureButtons() {
        Button addButton = new Button("Add List", click -> openAddDialog());
        Button deleteButton = new Button("Delete Selected", click -> deleteSelectedLists());
        Button backButton = new Button("Back", click -> getUI().ifPresent(ui -> ui.navigate("menu-usuario")));

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, deleteButton, backButton);
        add(buttonLayout);
    }

    private void updateList() {
        grid.setItems(giftListService.findByUser(user));
    }

    private void openAddDialog() {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField("List Name");

        Button saveButton = new Button("Save", event -> {
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
            Notification.show("Selected lists deleted");
        } else {
            Notification.show("Please select lists to delete");
        }
    }
}
