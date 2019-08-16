package de.cas.vaadin.thelibrary.modules;

import com.google.inject.AbstractModule;
import de.cas.vaadin.thelibrary.utils.EmailSender;
import de.cas.vaadin.thelibrary.utils.MailTrapSender;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(EmailSender.class).to(MailTrapSender.class);
    }
}
