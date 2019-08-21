package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.inject.Inject;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class NewRentalView extends VerticalLayout implements CreateContent {

    private final String name = "New Rentals";
    private RentalTab rentalTab;

    @Inject
    public NewRentalView(RentalTab rentalTab){
        this.rentalTab = rentalTab;
    }

    @Override
    public Component buildContent() {
        addComponent(rentalTab);
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.PLUS;
    }
}
