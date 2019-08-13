package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class NewBookView implements CreateContent {

    private HorizontalLayout mainLayout;

    @Override
    public Component buildContent() {
        mainLayout = new HorizontalLayout();

        mainLayout.addComponents(buildHeader());
        return mainLayout;
    }

    private Component buildHeader(){
        VerticalLayout header = new VerticalLayout();
        header.setStyleName("bookview-header");
        Label title = new Label();
        title.setStyleName(ValoTheme.LABEL_H2);
        header.addComponents(title, buildHeaderTab());

        return header;
    }

    private Component buildHeaderTab(){
        TabSheet tab = new TabSheet();
        tab.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        TextField searchField = new TextField();
        tab.addComponent(new HorizontalLayout(searchField));

        return tab;
    }


    @Override
    public String getName() {
        return "NewBookView";
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.BOOK;
    }
}
