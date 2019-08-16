package de.cas.vaadin.thelibrary.utils;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.model.bean.Reader;

import javax.mail.MessagingException;

public class SendMail {
    private EmailSender sender;

    @Inject
    public SendMail(EmailSender sender){
        this.sender = sender;
    }

    public void sendMail(Reader r) throws MessagingException {
        sender.send(r);
    }
}
