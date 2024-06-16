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
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
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

@PageTitle("Editar Regalos")
@Route(value = "edit-gift")
@PermitAll
@RequestScope
@CssImport("./styles/styles.css")
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
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        configureGrid();

    }

    @Override
    public void setParameter(BeforeEvent event, Long parameter) {
        this.giftListId = parameter;
        this.giftList = giftListService.getGiftListWithGifts(parameter);
        if (giftList != null) {

            H1 listMessage = new H1("Lista: " + giftList.getName());
            listMessage.addClassName("list-message");
            add(listMessage, createButtonLayout(), giftGrid);
            updateGiftList();
        }
    }

    private void configureGrid() {
        giftGrid.addClassName("gift-grid");
        giftGrid.setSizeFull();
        giftGrid.setColumns("name", "price", "status", "url");


        giftGrid.addColumn(gift -> gift.getRelative() != null ? gift.getRelative().getName() : "No hay allegado")
                .setHeader("Allegados");


        giftGrid.addComponentColumn(gift -> new Button("Editar", click -> openEditDialog(gift)))
                .setHeader("Acciones");


        giftGrid.setSelectionMode(Grid.SelectionMode.MULTI);

        add(giftGrid);
    }

    private HorizontalLayout createButtonLayout() {
        Button addButton = new Button("Añadir regalo", click -> openAddDialog());
        Button deleteButton = new Button("Eliminar regalos", click -> deleteSelectedGifts());
        Button backButton = new Button("Volver", click -> getUI().ifPresent(ui -> ui.navigate("gift-lists")));

        addButton.addClassName("red-button");
        deleteButton.addClassName("red-button");
        backButton.addClassName("red-button");

        HorizontalLayout buttonLayout = new HorizontalLayout(addButton, deleteButton, backButton);
        buttonLayout.setDefaultVerticalComponentAlignment(Alignment.CENTER);
        buttonLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        buttonLayout.setWidthFull();

        return buttonLayout;
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

        TextField nameField = new TextField("Nombre");
        TextField priceField = new TextField("Precio");
        ComboBox<Gift.Status> statusComboBox = new ComboBox<>("Estado", Gift.Status.values());
        ComboBox<Relative> relativeComboBox = new ComboBox<>("Allegado");
        TextField urlField = new TextField("URL");


        List<Relative> relatives = relativeService.getRelativesByUserId(user.getId());
        relativeComboBox.setItems(relatives);
        relativeComboBox.setItemLabelGenerator(Relative::getName);


        nameField.setRequired(true);
        priceField.setRequired(true);
        statusComboBox.setRequired(true);
        relativeComboBox.setRequired(true);
        urlField.setRequired(true);


        if (gift.getId() != null) {
            nameField.setValue(gift.getName());
            priceField.setValue(gift.getPrice().toString());
            statusComboBox.setValue(gift.getStatus());
            relativeComboBox.setValue(gift.getRelative());
            urlField.setValue(gift.getUrl());
        }

        Button saveButton = new Button("Guardar", event -> {
            if (nameField.isEmpty() || priceField.isEmpty() || statusComboBox.isEmpty() || relativeComboBox.isEmpty() || urlField.isEmpty()) {
                Notification.show("Todos los campos son requeridos.");
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
                Notification.show("Regalo " + (gift.getId() == null ? "añadido" : "actualizado") + " con éxito.");
            } catch (NumberFormatException e) {
                Notification.show("Formato de precio inválido. Introduzca un formato válido.");
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
            Notification.show("Regalos eliminados");
        } else {
            Notification.show("Selecciona regalos para eliminar");
        }
    }
}
