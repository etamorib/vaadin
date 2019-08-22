package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import de.cas.vaadin.thelibrary.modules.AppModule;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class NewRentalView extends VerticalLayout implements CreateContent {

    private final String name = "New Rentals";
    private RentalTab rentalTab;
    private Provider<RentalTab> rentalTabProvider;

    @Inject
    public NewRentalView(Provider<RentalTab> rentalTabProvider){

        this.rentalTabProvider = rentalTabProvider;
    }

    //Messy
    @Override
    public void buildContent() {
        rentalTab = rentalTabProvider.get();

        removeAllComponents();
        addComponent(rentalTab);

    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        buildContent();
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
