package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class Readers extends HorizontalLayout implements CreateContent {
	
	private final String name = "Readers";

	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("Readers"));
		return layout;
	}
	
	@Override
	public String getName() {
		return this.name;
	}
	

}
