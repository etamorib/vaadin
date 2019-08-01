package de.cas.vaadin.thelibrary.ui.view.content;


import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Book;
import de.cas.vaadin.thelibrary.bean.BookState;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


public class BooksView  implements CreateContent{
	private final String name ="Books";
	
	

	public BooksView() {
		
	}

	@Override
	public Component buildContent() {
		VerticalLayout layout = new VerticalLayout();
		layout.setSizeFull();
		Label pageTitle = new Label("Books in database");
		pageTitle.addStyleName(ValoTheme.LABEL_H1);
		pageTitle.addStyleName(ValoTheme.LABEL_BOLD);
		pageTitle.addStyleName(ValoTheme.LABEL_H1);
		layout.addComponent(pageTitle);
		
		Button addNew = new Button("Add Book");
		addNew.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		layout.addComponent(addNew);
		//TODO: add clicklistener to button
		
		Grid<Book> grid = new Grid<>();
		grid.addColumn(Book::getTitle).setCaption("Title");
		grid.addColumn(Book::getAuthor).setCaption("Author");
		grid.addColumn(Book::getId).setCaption("Id");
		grid.addColumn(Book::getYear).setCaption("Year");
		grid.addColumn(Book::getState).setCaption("State");

		
		//TODO: use database instead
		grid.setItems(new Book("sajt", "Lajos",123123, 1994, BookState.Available),
				new Book("asdasd", "Peter",412123, 2000, BookState.Available),
				new Book("history of cheese", "Cheezy",566123, 1667, BookState.Borrowed));
		

		grid.setSizeFull();
		layout.addComponent(grid);
		
		return layout;
	}

	@Override
	public String getName() {
		return this.name;
	}
	



	

	

}
