package com.example.dearsanta.gift.views;

import com.example.dearsanta.gift.models.Gift;
import com.example.dearsanta.gift.services.GiftService;
import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.services.GiftListService;
import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.relatives.service.RelativeService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@PageTitle("Edit Gifts")
@Route(value = "edit-gift")
@PermitAll
@RequestScope
public class EditGiftView extends VerticalLayout implements HasUrlParameter<Long> {

    private final GiftService giftService;
    private final GiftListService giftListService;
    private final AuthService authService;
    private final RelativeService relativeService;
    private GiftList giftList;
    private User user;

    private Grid<Gift> giftGrid = new Grid<>(Gift.class);
    private Long giftListId;

    @Autowired
    public EditGiftView(GiftService giftService, GiftListService giftListService, AuthService authService, RelativeService relativeService, HttpServletRequest request) {
        this.giftService = giftService;
        this.giftListService = giftListService;
        this.authService = authService;
        this.relativeService = relativeService;
        this.user = authService.getAuthenticatedUser(request);

        setSizeFull();
        configureGrid();
        configureButtons();
    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        this.giftListId = parameter;
        this.giftList = giftListService.getGiftListWithGifts(parameter);
        if (giftList != null) {
            add(new HorizontalLayout(new TextField("Lista de regalos: " + giftList.getName())));
            updateGiftList();
        }
    }

    private void configureGrid() {
        giftGrid.addClassName("gift-grid");
        giftGrid.setSizeFull();
        giftGrid.setColumns("name", "price", "status", "url");

        // Custom column for relative name
        giftGrid.addColumn(gift -> gift.getRelative() != null ? gift.getRelative().getName() : "No Relative")
                .setHeader("Relative");

        // Add an edit button column
        giftGrid.addComponentColumn(gift -> new Button("Edit", click -> openEditDialog(gift)))
                .setHeader("Actions");

        // Add checkboxes for multiple selection
        giftGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        add(giftGrid);
    }

    private void configureButtons() {
        Button addButton = new Button("Add Gift", click -> openAddDialog());
        Button deleteButton = new Button("Delete Selected Gifts", click -> deleteSelectedGifts());
        Button backButton = new Button("Back", click -> getUI().ifPresent(ui -> ui.navigate("gift-lists")));

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, deleteButton, backButton);
        add(buttonLayout);
    }

    private void updateGiftList() {
        if (giftList != null) {
            giftGrid.setItems(giftList.getGifts());
        }
    }

    private void openAddDialog() {
        openGiftDialog(new Gift());
    }

    private void openEditDialog(Gift gift) {
        openGiftDialog(gift);
    }

    private void openGiftDialog(Gift gift) {
        Dialog dialog = new Dialog();
        FormLayout formLayout = new FormLayout();

        TextField nameField = new TextField("Gift Name");
        TextField priceField = new TextField("Price");
        ComboBox<Gift.Status> statusComboBox = new ComboBox<>("Status", Gift.Status.values());
        ComboBox<Relative> relativeComboBox = new ComboBox<>("Relative");
        TextField urlField = new TextField("URL");

        // Load relatives of the authenticated user
        List<Relative> relatives = relativeService.getRelativesByUserId(user.getId());
        relativeComboBox.setItems(relatives);
        relativeComboBox.setItemLabelGenerator(Relative::getName);

        // Make fields required
        nameField.setRequired(true);
        priceField.setRequired(true);
        statusComboBox.setRequired(true);
        relativeComboBox.setRequired(true);
        urlField.setRequired(true);

        // Pre-fill fields if editing
        if (gift.getId() != null) {
            nameField.setValue(gift.getName());
            priceField.setValue(gift.getPrice().toString());
            statusComboBox.setValue(gift.getStatus());
            relativeComboBox.setValue(gift.getRelative());
            urlField.setValue(gift.getUrl());
        }

        Button saveButton = new Button("Save", event -> {
            if (nameField.isEmpty() || priceField.isEmpty() || statusComboBox.isEmpty() || relativeComboBox.isEmpty() || urlField.isEmpty()) {
                Notification.show("All fields are required.");
                return;
            }

            try {
                gift.setName(nameField.getValue());
                gift.setPrice(new BigDecimal(priceField.getValue().replace(",", "."))); // Convert comma to dot
                gift.setStatus(statusComboBox.getValue());
                gift.setRelative(relativeComboBox.getValue());
                gift.setUrl(urlField.getValue());
                gift.setGiftList(giftList);

                giftService.save(gift);
                updateGiftList();
                giftListService.updateGiftListStatus(giftList);
                dialog.close();
                Notification.show("Gift " + (gift.getId() == null ? "added" : "updated") + " successfully.");
            } catch (NumberFormatException e) {
                Notification.show("Invalid price format. Please enter a valid number.");
            }
        });

        formLayout.add(nameField, priceField, statusComboBox, relativeComboBox, urlField, saveButton);
        dialog.add(formLayout);
        dialog.open();
    }

    private void deleteSelectedGifts() {
        Set<Gift> selectedGifts = giftGrid.getSelectedItems();
        if (!selectedGifts.isEmpty()) {
            for (Gift gift : selectedGifts) {
                giftService.deleteById(gift.getId());
            }
            updateGiftList();
            giftListService.updateGiftListStatus(giftList);
            Notification.show("Selected gifts deleted");
        } else {
            Notification.show("Please select gifts to delete");
        }
    }
}
