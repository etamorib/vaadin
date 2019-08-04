package de.cas.vaadin.thelibrary.ui.view;

import com.vaadin.server.Resource;
import com.vaadin.ui.Component;

public interface CreateContent {
	
	Component buildContent();
	String getName();
	Resource menuIcon();

}
