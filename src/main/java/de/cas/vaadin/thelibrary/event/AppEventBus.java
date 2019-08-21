package de.cas.vaadin.thelibrary.event;

import com.google.common.eventbus.EventBus;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.CASTheLibraryApplication;

/**
 * @author mate.biro
 * This class simply customises the guava EventBus's
 * register<br>
 * unregister<br>
 * post<br>
 * methods, so it uses the methods of the EventBus created
 * inside the CASTheLibraryApplication class
 *
 */
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
