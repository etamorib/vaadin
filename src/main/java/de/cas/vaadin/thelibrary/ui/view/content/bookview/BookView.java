package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.inject.Inject;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class BookView extends HorizontalLayout implements CreateContent {

    private final String name = "Books";
    private BookContent bookContent;

    @Inject
    public BookView(BookContent bookContent){
        this.bookContent = bookContent;
    }

    @Override
    public Component buildContent() {
        addComponent(bookContent);
        setSizeFull();
        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.BOOK;
    }
}
