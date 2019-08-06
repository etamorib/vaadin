package de.cas.vaadin.thelibrary.ui.view.content;

import java.util.LinkedHashSet;
import java.util.Set;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.ReaderController;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

@SuppressWarnings("serial")
public class NewRental extends HorizontalLayout implements CreateContent {
	
	private final int maxSelect = 5;
	private final String name = "New Rentals";
	private VerticalLayout mainLayout;
	private TabSheet tab;
	private VerticalLayout books, readers, deadline;
	private BookController bookController = new BookController();
	private ReaderController readerController = new ReaderController();
	private ListDataProvider<Book> bookDataProvider ;
	private ListDataProvider<Reader> readerDataProvider;
	private Reader selectedReader = null;
	private Grid<Book> bookGrid;
	private Grid<Reader> readerGrid;
	private ListSelect<Book> list = null;
	
	
	@Override
	public Component buildContent() {
		Label title = new Label("Add new rentals");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		mainLayout = new VerticalLayout(title);
		tab = new TabSheet();
		tab.setSizeFull();
		tab.setResponsive(true);
		
		
		//Book tab
		books = new VerticalLayout();
		books.addComponent(booksLayout());
		tab.addTab(books).setIcon(VaadinIcons.BOOK);
		
		//Reader tab
		readers = new VerticalLayout();
		readers.addComponent(readersLayout());
		tab.addTab(readers).setIcon(VaadinIcons.USERS);
		
		tab.getTab(readers).setEnabled(false);

		//Deadline tab
		deadline = new VerticalLayout(new Label("Deadline"));
		tab.addTab(deadline).setIcon(VaadinIcons.CALENDAR_CLOCK);
		tab.getTab(deadline).setEnabled(false);

		//So selected books, reader can be displayed on
		//other tabs
		tab.addSelectedTabChangeListener(listener->{
			updateLayouts();
		});
		
		mainLayout.addComponents(tab);
		return mainLayout;
	}
	
	private void updateLayouts() {
		readers.removeAllComponents();
		readers.addComponent(readersLayout());
		
		deadline.removeAllComponents();
		deadline.addComponent(deadlineLayout());
		
	}
	
	/*BUILDING BOOKS TAB*/
	
	private Component deadlineLayout() {
		VerticalLayout deadline = new VerticalLayout();
		return deadline;
	}

	private Component booksLayout() {
		//Returned main layout
		VerticalLayout books = new VerticalLayout();
		
		Label title = new Label("Select books that want to be rented ");
		title.setStyleName(ValoTheme.LABEL_H2);
		
		//Counter for selected items
		Label counter = new Label();
		
		//Button for adding
		Button add = new Button("Select books");
		add.setStyleName("select-book-button");
		add.setSizeFull();
		add.setEnabled(false);
		
		//Grid
		bookGrid = new Grid<>(Book.class);
		bookDataProvider = new ListDataProvider<>(bookController.getItems());
		//To make sure column order
		bookGrid.setColumns("author","title", "id", "year", "state");
		bookGrid.setSelectionMode(SelectionMode.MULTI);
		bookGrid.setStyleName("grid-overall");
		bookGrid.setDataProvider(bookDataProvider);
		bookGrid.setSizeFull();
		//Filter
		bookDataProvider.setFilter(book->book.getState() == BookState.Available || book.getState()==BookState.Borrowed);
		NativeSelect<BookState> state = new NativeSelect<>();
		state.setItems(BookState.Available, BookState.Borrowed);
		state.addSelectionListener(e->{
			if(e.getValue()!=null) {
				bookDataProvider.setFilter(book -> book.getState() ==e.getValue());
			}else {
				bookDataProvider.setFilter(book->book.getState() == BookState.Available || book.getState()==BookState.Borrowed);

			}
			
		});
		
		//Grid selection listener, to prevent more than 5 selects
		bookGrid.addSelectionListener(e->{
			int currentlySelected = bookGrid.getSelectedItems().size();
			
			updateCounter(counter, currentlySelected);
			if(currentlySelected> maxSelect) {
				bookGrid.deselect(bookGrid.getSelectedItems().iterator().next());
			}
			if(currentlySelected > 0) {
				//Adding selected items to the ListSelect
				list = new ListSelect<>();
				list.setItems(bookGrid.getSelectedItems());
				list.setSizeFull();
	
				//Handling further actions
				add.setEnabled(true);
				add.addClickListener(event->{
					tab.getTab(readers).setEnabled(true);
					tab.setSelectedTab(readers);

				});
			}else {
				tab.getTab(readers).setEnabled(false);
			}
		});
		
		
		bookGrid.addHeaderRowAt(1);
		bookGrid.getHeaderRow(1).getCell("state").setComponent(state);
		
		//Search field
		TextField search  = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setPlaceholder("Search...");
		search.setValueChangeMode(ValueChangeMode.EAGER);
		search.addValueChangeListener(e->{
			//Filter for both title and author
			bookDataProvider.setFilter(book -> book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
					book.getTitle().toLowerCase().contains(e.getValue().toLowerCase()));
					
		});
		
		books.addComponents(title, search, bookGrid, counter, add);
		return books;
	}
	
	private void updateCounter(Label l, int i) {
		l.setCaption(i+" items are selected, "+ (maxSelect-i)+" remaining.");
		
	}
	
	/*BUILDING READERS TAB*/
	
	private Component readersLayout() {
		//Returned main layout
		VerticalLayout readers = new VerticalLayout();
		System.out.println(bookGrid.getSelectedItems());
		
		//Check is list is null
		//It should never be null, but safety first :)
		if(list !=null) {
			Label currentItems = new Label("Currently selected books:");
			currentItems.setStyleName(ValoTheme.LABEL_H2);
			readers.addComponent(currentItems);
			readers.addComponent(list);
		}
		
		//Title
		Label title = new Label("Select the reader");
		title.setStyleName(ValoTheme.LABEL_H2);
		
		//Add reader button
		Button add = new Button("Select reader");
		add.setSizeFull();
		
		//Reader grid
		readerGrid = new Grid<>(Reader.class);
		readerDataProvider = new ListDataProvider<>(readerController.getItems());
		readerGrid.setSelectionMode(SelectionMode.SINGLE);
		readerGrid.setStyleName("grid-overall single-select");
		readerGrid.setDataProvider(readerDataProvider);
		readerGrid.setSizeFull();

		//continue if reader is selected
		readerGrid.addSelectionListener(e->{
			if(readerGrid.getSelectedItems().size()>0) {
				add.addClickListener(event->{
					tab.getTab(deadline).setEnabled(true);
					tab.setSelectedTab(deadline);
				});
			}else {
				tab.getTab(deadline).setEnabled(false);
			}
		});
		
		//Searchfield
		TextField search  = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setPlaceholder("Find a reader...");
		search.setValueChangeMode(ValueChangeMode.EAGER);
		search.addValueChangeListener(e->{
			//Filter for both title and author
			readerDataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));		
			
		});
		
		//TODO: maybe shortcut to register user here?
		
		
		
		readers.addComponents(title,search ,readerGrid, add);
		return readers;
		
	}
	

	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Resource menuIcon() {
		return VaadinIcons.PLUS;
	}
	
	

}
