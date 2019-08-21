package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;

public class BookContent extends VerticalLayout {

    private BookMenu bookMenu;
    private BookGrid bookGrid;

    @Inject
    public BookContent(BookMenu bookMenu, BookGrid bookGrid){
        this.bookGrid = bookGrid;
        this.bookMenu = bookMenu;
        setCaptionAsHtml(true);
        setCaption("<h1>Books in database</h1>");
        addComponents(bookMenu, bookGrid);
    }
}
