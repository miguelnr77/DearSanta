package com.example.dearsanta.gift.views;

import com.example.dearsanta.Views.MainView;
import com.example.dearsanta.gift.models.Gift;
import com.example.dearsanta.gift.services.GiftService;
import com.example.dearsanta.list.services.GiftListService;
import com.example.dearsanta.relatives.models.Relative;
import com.example.dearsanta.relatives.service.RelativeService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

@PageTitle("Edit Gift")
@Route(value = "edit-gift/:giftId")
@PermitAll
public class EditGiftView extends FormLayout implements BeforeEnterObserver {

    private final GiftService giftService;
    private final GiftListService giftListService;
    private final RelativeService relativeService;
    private final AuthService authService;

    private Gift currentGift;

    private TextField name = new TextField("Name");
    private TextField status = new TextField("Status");
    private TextField price = new TextField("Price");
    private TextField url = new TextField("URL");
    private ComboBox<Relative> relativeComboBox = new ComboBox<>("Relative");

    private Button saveButton = new Button("Save");

    @Autowired
    public EditGiftView(GiftService giftService, GiftListService giftListService, RelativeService relativeService, AuthService authService, HttpServletRequest request) {
        this.giftService = giftService;
        this.giftListService = giftListService;
        this.relativeService = relativeService;
        this.authService = authService;

        User user = authService.getAuthenticatedUser(request);
        if (user != null) {
            List<Relative> relatives = relativeService.getRelativesByUserId(user.getId());
            relativeComboBox.setItems(relatives);
            relativeComboBox.setItemLabelGenerator(Relative::getName);
        }

        saveButton.addClickListener(e -> saveGift());

        add(name, status, price, url, relativeComboBox, saveButton);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long giftId = event.getRouteParameters().get("giftId").map(Long::parseLong).orElse(null);
        if (giftId != null) {
            Gift gift = giftService.findById(giftId);
            if (gift != null) {
                setGift(gift);
            } else {
                Notification.show("Gift not found");
                event.forwardTo("gift-lists");
            }
        }
    }

    private void saveGift() {
        if (currentGift != null) {
            currentGift.setName(name.getValue());
            currentGift.setStatus(Gift.Status.valueOf(status.getValue()));
            currentGift.setPrice(new BigDecimal(price.getValue()));
            currentGift.setUrl(url.getValue());
            currentGift.setRelative(relativeComboBox.getValue());

            giftService.save(currentGift);
            Notification.show("Gift saved");
        }
    }

    public void setGift(Gift gift) {
        this.currentGift = gift;
        name.setValue(gift.getName());
        status.setValue(gift.getStatus().name());
        price.setValue(String.valueOf(gift.getPrice()));
        url.setValue(gift.getUrl());
        relativeComboBox.setValue(gift.getRelative());
    }
}
