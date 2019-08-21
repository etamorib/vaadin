package de.cas.vaadin.thelibrary.handler;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class HandlerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(AuthenticationInterface.class, AuthenticationHandler.class)
                .build(AuthenticationFactory.class));
    }

}
