package de.cas.vaadin.thelibrary.utils;

import de.cas.vaadin.thelibrary.model.bean.Reader;

import javax.mail.MessagingException;
import javax.mail.Session;

public interface EmailSender {

    Session configureSession(String username, String password);
    void send(Reader r) throws MessagingException;
}
