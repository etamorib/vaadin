package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class WaitList extends HorizontalLayout implements CreateContent {
	
	private final String name = "Waitlists";

	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("WaitList"));
		return layout;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

}
