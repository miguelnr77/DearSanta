package com.example.dearsanta.list.views;

import com.example.dearsanta.Views.MainView;
import com.example.dearsanta.list.models.GiftList;
import com.example.dearsanta.list.services.GiftListService;
import com.example.dearsanta.users.models.User;
import com.example.dearsanta.users.services.AuthService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.annotation.RequestScope;

@PageTitle("Gift Lists")
@Route(value = "gift-lists")
@PermitAll
@RequestScope
public class GiftListView extends VerticalLayout {

    private final GiftListService giftListService;
    private final AuthService authService;
    private Grid<GiftList> grid = new Grid<>(GiftList.class);

    @Autowired
    public GiftListView(GiftListService giftListService, AuthService authService, HttpServletRequest request) {
        this.giftListService = giftListService;
        this.authService = authService;
        User user = authService.getAuthenticatedUser(request);
        if (user != null) {
            addClassName("list-view");
            setSizeFull();
            configureGrid();
            add(grid);
            updateList(user);
        } else {
            // Manejar el caso en que no hay usuario autenticado
        }
    }

    private void configureGrid() {
        grid.addClassName("gift-list-grid");
        grid.setSizeFull();
        grid.setColumns("name", "status");

        grid.addComponentColumn(giftList -> {
            Button editButton = new Button("Edit");
            editButton.addClickListener(e -> editGiftList(giftList));
            return editButton;
        });
    }

    private void updateList(User user) {
        grid.setItems(giftListService.findByUser(user));
    }

    private void editGiftList(GiftList giftList) {
        getUI().ifPresent(ui -> ui.navigate("edit-gift/" + giftList.getId()));
    }
}
