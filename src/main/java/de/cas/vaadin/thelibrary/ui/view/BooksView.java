package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class BooksView extends Composite implements View {
	
	public BooksView() {
		setCompositionRoot(new Label("Books"));
	}

}
