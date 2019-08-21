package de.cas.vaadin.thelibrary.modules;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import de.cas.vaadin.thelibrary.handler.HandlerModule;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
import de.cas.vaadin.thelibrary.ui.view.ViewModule;
import de.cas.vaadin.thelibrary.ui.view.content.*;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookView;
import de.cas.vaadin.thelibrary.utils.EmailSender;
import de.cas.vaadin.thelibrary.utils.MailTrapSender;
import de.cas.vaadin.thelibrary.utils.UtilsModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ViewModule());
        install(new HandlerModule());
        install(new UtilsModule());

        //Views
        Multibinder<CreateContent> contentBinder = Multibinder.newSetBinder(binder(), CreateContent.class);
        contentBinder.addBinding().to(BookView.class);
        //contentBinder.addBinding().to(NewRental.class);
        contentBinder.addBinding().to(Readers.class);
        contentBinder.addBinding().to(Rentals.class);
        contentBinder.addBinding().to(WaitList.class);

        //bind(Button.class).to(NativeButton.class);


    }
}
