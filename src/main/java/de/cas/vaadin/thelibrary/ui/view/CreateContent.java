package de.cas.vaadin.thelibrary.ui.view;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
/**
 * @author mate.biro
 * Interface for classes representing views
 *
 */
public interface CreateContent {
	
	Component buildContent();
	String getName();
	Resource menuIcon();

}
