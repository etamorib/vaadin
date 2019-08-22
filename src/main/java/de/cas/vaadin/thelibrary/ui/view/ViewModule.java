package de.cas.vaadin.thelibrary.ui.view;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.vaadin.ui.*;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.content.Readers;
import de.cas.vaadin.thelibrary.ui.view.content.Rentals;
import de.cas.vaadin.thelibrary.ui.view.content.WaitList;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookView;
import de.cas.vaadin.thelibrary.ui.view.content.newrental.NewRentalView;
import de.cas.vaadin.thelibrary.ui.view.content.readerview.ReaderView;

public class ViewModule extends AbstractModule {
    @Override
    protected void configure() {
        //Views
        Multibinder<CreateContent> contentBinder = Multibinder.newSetBinder(binder(), CreateContent.class);
        contentBinder.addBinding().to(BookView.class);
        contentBinder.addBinding().to(NewRentalView.class);
        contentBinder.addBinding().to(ReaderView.class);
        contentBinder.addBinding().to(Rentals.class);
        contentBinder.addBinding().to(WaitList.class);
    }

    @Provides
    Grid<Book> provideBookGrid(){
        return new Grid<>(Book.class);
    }

    @Provides
    Grid<Reader> provideReaderGrid(){
        return new Grid<>(Reader.class);
    }
}
