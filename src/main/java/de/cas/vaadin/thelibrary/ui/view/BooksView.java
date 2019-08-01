package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Component;
import com.vaadin.ui.Composite;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;


public class BooksView extends HorizontalLayout implements CreateContent{
	public static final String name ="Books";

	public BooksView() {
		
	}

	@Override
	public Component buildContent() {
		CssLayout layout = new CssLayout();
		layout.addComponent(new TextField());
		layout.addComponent(new Label("Books"));
		return layout;
	}
	



	

	

}
