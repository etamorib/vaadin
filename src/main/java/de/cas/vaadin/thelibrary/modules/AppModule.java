package de.cas.vaadin.thelibrary.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.vaadin.ui.Button;
import com.vaadin.ui.NativeButton;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
import de.cas.vaadin.thelibrary.ui.view.content.*;
import de.cas.vaadin.thelibrary.utils.EmailSender;
import de.cas.vaadin.thelibrary.utils.MailTrapSender;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EmailSender.class).to(MailTrapSender.class);
        //Views
        Multibinder<CreateContent> contentBinder = Multibinder.newSetBinder(binder(), CreateContent.class);
        contentBinder.addBinding().to(BooksView.class);
        contentBinder.addBinding().to(NewRental.class);
        contentBinder.addBinding().to(Readers.class);
        contentBinder.addBinding().to(Rentals.class);
        contentBinder.addBinding().to(WaitList.class);

        //bind(Button.class).to(NativeButton.class);
    }
}
