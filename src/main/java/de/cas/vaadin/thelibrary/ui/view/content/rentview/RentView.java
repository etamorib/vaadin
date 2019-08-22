package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class RentView extends HorizontalLayout implements CreateContent {

    private final String name ="Rentals";
    private RentContent rentContent;
    private Provider<RentContent> rentContentProvider;

    @Inject
    public RentView(Provider<RentContent> rentContentProvider) {
        this.rentContentProvider = rentContentProvider;
    }

    @Override
    public void buildContent() {
        rentContent = rentContentProvider.get();
        removeAllComponents();
        addComponent(rentContent);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.REPLY_ALL;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        buildContent();
    }
}
