package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

public class BooksView extends Composite implements View {
	public static String name ="BooksView";
	public BooksView() {
		setCompositionRoot(new Label("ASSD"));
		
	}

	@Override
	public String toString() {
		return name;
	}

}
