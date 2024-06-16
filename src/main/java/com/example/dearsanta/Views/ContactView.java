package com.example.dearsanta.Views;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
@PageTitle("Contacto")
@Route("contact")
@CssImport("./styles/styles.css")
public class ContactView extends VerticalLayout {

    public ContactView() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setSizeFull();

        // Header
        Div header = new Div();
        header.setText("DearSanta");
        header.addClassName("header");

        // Navigation Bar
        HorizontalLayout navBar = new HorizontalLayout();
        navBar.addClassName("nav-bar");
        Anchor homeLink = new Anchor("", "Inicio");
        Anchor aboutLink = new Anchor("about", "Acerca De");
        Anchor contactLink = new Anchor("contact", "Contacto");
        navBar.add(homeLink, aboutLink, contactLink);

        // Footer
        Div footer = new Div();
        footer.setText("© 2024 DearSanta. ");
        footer.addClassName("footer");

        // Main Content
        VerticalLayout mainContent = new VerticalLayout();
        mainContent.addClassName("main-content");

        H1 title = new H1("Contacto");
        Paragraph description = new Paragraph("Av. Universidad de Cádiz, 10, 11519 Puerto Real, Cádiz Tlf: 956 48 32 00");

        mainContent.add(title, description);

        add(header, navBar, mainContent, footer);
    }
}
