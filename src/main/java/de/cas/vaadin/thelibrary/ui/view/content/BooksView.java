package de.cas.vaadin.thelibrary.ui.view.content;



import java.util.Set;

import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Book;
import de.cas.vaadin.thelibrary.bean.BookState;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


public class BooksView implements CreateContent{
	private final String name ="Books";
	private Grid<Book> grid;
	

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
		addNew.addClickListener(e->{
			addNewPopup();
		});
		
		//Grid to show data
		grid = new Grid<>(Book.class);
		grid.setColumns("title", "author", "id", "year", "state");
		grid.getEditor().setEnabled(true);
		
		
		//TODO: use database instead
		grid.setItems(new Book("sajt", "Lajos",123123, 1994, BookState.Available),
				new Book("asdasd", "Peter",412123, 2000, BookState.Available),
				new Book("history of cheese", "Cheezy",566123, 1667, BookState.Borrowed));

		//Edit grid
		grid.getColumn("title").setEditorComponent(new TextField());
		grid.getEditor().addSaveListener(listener ->{
			System.out.println("SAJTOS!");
			System.out.println(grid.getSelectedItems());
			Set<Book> o = grid.getSelectedItems();

			for(Book b : o) {
				System.out.println(b);
			}
			
		});
				
		grid.setSizeFull();
		layout.addComponent(grid);
		
		return layout;
	}

	//TODO : finish form, add clicklistener to Add button
	private void addNewPopup() {
		Window window = new Window();
		VerticalLayout layout = new VerticalLayout();
		layout.addComponents(new Label("Title:"),new TextField(), new Button("Add"));
		window.setContent(layout);
		window.setResizable(false);
		window.center();
		window.setDraggable(false);
		window.setModal(true);
		
		UI.getCurrent().addWindow(window);
		
		
			
	}

	@Override
	public String getName() {
		return this.name;
	}
	
	

}