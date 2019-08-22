package de.cas.vaadin.thelibrary.ui.view;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.vaadin.ui.Grid;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookView;
import de.cas.vaadin.thelibrary.ui.view.content.newrental.NewRentalView;
import de.cas.vaadin.thelibrary.ui.view.content.readerview.ReaderView;
import de.cas.vaadin.thelibrary.ui.view.content.rentview.RentView;

public class ViewModule extends AbstractModule {
    @Override
    protected void configure() {
        //Views
        Multibinder<CreateContent> contentBinder = Multibinder.newSetBinder(binder(), CreateContent.class);
        contentBinder.addBinding().to(BookView.class);
        contentBinder.addBinding().to(NewRentalView.class);
        contentBinder.addBinding().to(ReaderView.class);
        contentBinder.addBinding().to(RentView.class);
        //contentBinder.addBinding().to(WaitList.class);
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
