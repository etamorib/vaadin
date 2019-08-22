package de.cas.vaadin.thelibrary.modules;

import com.google.inject.AbstractModule;
import de.cas.vaadin.thelibrary.handler.HandlerModule;
import de.cas.vaadin.thelibrary.ui.view.ViewModule;
import de.cas.vaadin.thelibrary.utils.UtilsModule;

public class AppModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ViewModule());
        install(new HandlerModule());
        install(new UtilsModule());

    }


}
