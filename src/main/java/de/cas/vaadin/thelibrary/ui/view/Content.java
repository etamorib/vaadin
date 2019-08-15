package de.cas.vaadin.thelibrary.ui.view;

import com.google.inject.Inject;
import com.vaadin.ui.Component;

/**
 * @author mate.biro
 * Controls the content building method of the
 * different classes.
 */
class Content {

	private CreateContent content;

	@Inject
	Content(CreateContent content) {
		this.content = content;
	}
	
	Component createContent() {
		return this.content.buildContent();
	}
		
	
	
}
