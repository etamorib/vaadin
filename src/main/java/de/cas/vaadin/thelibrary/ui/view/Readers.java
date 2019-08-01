package de.cas.vaadin.thelibrary.ui.view;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;

public class Readers extends HorizontalLayout implements CreateContent {
	
	public static final String name = "Readers";

	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("Readers"));
		SideMenuBuilder.Instance().addMenuItem(this, new Button(name));
		return layout;
	}
	

}
