package de.cas.vaadin.thelibrary.ui.view.content.readerview;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookGrid;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookMenu;

public class ReaderContent extends VerticalLayout {

    private ReaderMenu readerMenu;
    private ReaderGrid readerGrid;
    @Inject
    public ReaderContent(ReaderMenu readerMenu, ReaderGrid readerGrid){
        this.readerGrid = readerGrid;
        this.readerMenu = readerMenu;
        setCaptionAsHtml(true);
        setCaption("<h1>Readers in database</h1>");
        addComponents(this.readerMenu, this.readerGrid);
    }
}
