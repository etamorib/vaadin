package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import de.cas.vaadin.thelibrary.ui.view.CreateContent;

@SuppressWarnings("serial")
public class Readers extends HorizontalLayout implements CreateContent {
	
	private final String name = "Readers";
	private VerticalLayout layout = new VerticalLayout();
	@Override
	public Component buildContent() {
		for(int i =0; i<100;i++) {
			layout.addComponent(new Button("SAJT"));
		}
		return layout;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Resource menuIcon() {
		return VaadinIcons.USERS;
	}
	

}
