package de.cas.vaadin.thelibrary.handler;

import com.vaadin.ui.Notification;
import de.cas.vaadin.thelibrary.model.bean.Admin;

public interface AuthenticationInterface {
    Notification check();
    Admin authenticate();
}
