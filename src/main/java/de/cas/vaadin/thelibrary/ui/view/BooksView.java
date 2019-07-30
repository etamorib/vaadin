package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import de.cas.vaadin.thelibrary.ui.view.wrapper.AbstractViewWrapper;

public class BooksView extends AbstractViewWrapper{
	private final String name ="BooksView";

	public BooksView() {
		System.out.println("ASDASD");
	}
	
	@Override
	public String name() {
		return name;
	}


	

	

}
