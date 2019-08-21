package de.cas.vaadin.thelibrary.utils;

import com.google.inject.AbstractModule;

public class UtilsModule extends AbstractModule {
    @Override
    public void configure(){
        bind(EmailSender.class).to(MailTrapSender.class);
    }
}
