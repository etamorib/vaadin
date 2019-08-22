package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import de.cas.vaadin.thelibrary.modules.AppModule;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class BookView extends HorizontalLayout implements CreateContent{

    private final String name = "Books";
    private BookContent bookContent;
    private Provider<BookContent> bookContentProvider;

    @Inject
    public BookView(Provider<BookContent> bookContentProvider){
        this.bookContentProvider = bookContentProvider;
    }

    @Override
    public void buildContent() {
        bookContent = bookContentProvider.get();
        removeAllComponents();
        addComponent(bookContent);
        setSizeFull();
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
        return VaadinIcons.BOOK;
    }
}
