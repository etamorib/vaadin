package de.cas.vaadin.thelibrary.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
import de.cas.vaadin.thelibrary.ui.view.content.*;
import de.cas.vaadin.thelibrary.utils.EmailSender;
import de.cas.vaadin.thelibrary.utils.MailTrapSender;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EmailSender.class).to(MailTrapSender.class);
        Multibinder<CreateContent> contentBinder = Multibinder.newSetBinder(binder(), CreateContent.class);
        contentBinder.addBinding().toInstance(new BooksView());
        contentBinder.addBinding().toInstance(new NewRental());
        contentBinder.addBinding().toInstance(new Readers());
        contentBinder.addBinding().toInstance(new Rentals());
        contentBinder.addBinding().toInstance(new WaitList());

    }
}
