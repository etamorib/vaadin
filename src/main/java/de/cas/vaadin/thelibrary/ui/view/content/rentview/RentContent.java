package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.model.bean.Rent;

public class RentContent extends VerticalLayout {

    private RentMenu rentMenu;
    private OngoingGrid ongoingGrid;
    private LateGrid lateGrid;
    private Provider<Panel> panelProvider;
    private Provider<Button> buttonProvider;
    @Inject
    public RentContent(RentMenu rentMenu,
                       OngoingGrid ongoingGrid,
                       LateGrid lateGrid,
                       Provider<Button> buttonProvider){

        this.rentMenu = rentMenu;
        this.ongoingGrid = ongoingGrid;
        this.lateGrid = lateGrid;
        this.buttonProvider = buttonProvider;
        setCaptionAsHtml(true);
        setCaption("<h1>Rents in database</h1>");
        buildLayout();
    }

    private void buildLayout() {

        addComponents(this.ongoingGrid, this.lateGrid);

    }

}
