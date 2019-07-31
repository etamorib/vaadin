package de.cas.vaadin.thelibrary.event;

import com.google.common.eventbus.EventBus;

import de.cas.vaadin.thelibrary.CASTheLibraryApplication;

public class AppEventBus {
	
private final EventBus eventBus = new EventBus();
	
	public static void register(final Object object) {
	    CASTheLibraryApplication.getEventBus().eventBus.register(object);
	}

	public static void unregister(final Object object) {
		CASTheLibraryApplication.getEventBus().eventBus.unregister(object);
	}
	
	public static void post(final Object event) {
		CASTheLibraryApplication.getEventBus().eventBus.post(event);
	}
	

}
