package de.cas.vaadin.thelibrary.handler;

import com.google.inject.assistedinject.Assisted;

public interface AuthenticationFactory {
    AuthenticationInterface create(@Assisted("username") String username, @Assisted("password") String password);
}
