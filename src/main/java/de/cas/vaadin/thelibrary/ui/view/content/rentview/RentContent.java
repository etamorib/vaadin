package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;

public class RentContent extends VerticalLayout {

    private RentMenu rentMenu;
    private OngoingGrid ongoingGrid;
    @Inject
    public RentContent(RentMenu rentMenu, OngoingGrid ongoingGrid){

        this.rentMenu = rentMenu;
        this.ongoingGrid = ongoingGrid;
        setCaptionAsHtml(true);
        setCaption("<h1>Rents in database</h1>");
        addComponents(this.rentMenu, this.ongoingGrid);
    }

}
