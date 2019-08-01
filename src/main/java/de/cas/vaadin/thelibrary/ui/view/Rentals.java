package de.cas.vaadin.thelibrary.ui.view;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class Rentals extends HorizontalLayout implements CreateContent {

	public static final String name = "Rentals";
	
	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("Rentals"));
		return layout;
	}
	
	

}