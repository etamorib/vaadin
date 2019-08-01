package de.cas.vaadin.thelibrary.ui.view;

import com.vaadin.ui.Component;

public class Content {

	private CreateContent content;
	
	public Content(CreateContent content) {
		this.content = content;
	}
	
	public Component createContent() {
		return this.content.buildContent();
	}
		
	
	
}
