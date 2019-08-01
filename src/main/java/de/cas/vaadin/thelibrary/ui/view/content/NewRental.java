package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.cas.vaadin.thelibrary.ui.view.CreateContent;

@SuppressWarnings("serial")
public class NewRental extends HorizontalLayout implements CreateContent {
	
	private final String name = "New Rentals";

	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("New Rental"));
		return layout;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	
	

}
