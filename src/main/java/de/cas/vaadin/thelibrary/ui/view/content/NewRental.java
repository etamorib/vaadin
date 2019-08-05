package de.cas.vaadin.thelibrary.ui.view.content;

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
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
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
	private Set<Book> selectedBooks = null;
	private Reader selectedReader = null;
	private Grid<Book> bookGrid;
	
	
	@Override
	public Component buildContent() {
		Label title = new Label("Add new rentals");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		mainLayout = new VerticalLayout(title);
		tab = new TabSheet();
		tab.setSizeFull();
		tab.setResponsive(true);
		tab.addSelectedTabChangeListener(e->{
			buildReaders();
		});
		
		
		//Book tab
		books = new VerticalLayout();
		books.addComponent(booksLayout());
		tab.addTab(books).setIcon(VaadinIcons.BOOK);
		
		//Reader tab
		readers = new VerticalLayout(new Label("Readers"));
		tab.addTab(readers).setIcon(VaadinIcons.USERS);
		
		tab.getTab(readers).setEnabled(false);
		
		//TODO: igy lehet uj tabra menni
		//TODO: ez igy nem jo, selectionre kell a listener hogyha 0
		//akkor letiltsa a továbbmenetelt
		Button b = new Button("Add");
		b.addClickListener(e->{
			if(bookGrid.getSelectedItems().size()>0) {
				tab.getTab(readers).setEnabled(true);
				tab.setSelectedTab(readers);
			}else {
				tab.getTab(readers).setEnabled(false);
				System.out.println("HIBA!");
			}

		});
		
		//Deadline tab
		deadline = new VerticalLayout(new Label("Deadline"));
		tab.addTab(deadline).setIcon(VaadinIcons.CALENDAR_CLOCK);
		
		mainLayout.addComponents(tab,b);
		return mainLayout;
	}
	
	private Component booksLayout() {
		VerticalLayout books = new VerticalLayout();
		Label title = new Label("Select books that want to be rented ");
		title.setStyleName(ValoTheme.LABEL_H2);
		Label counter = new Label();
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
		
		books.addComponents(title, search, bookGrid, counter);
		return books;
	}
	
	private void updateCounter(Label l, int i) {
		l.setCaption(i+" items are selected, "+ (maxSelect-i)+" remaining.");
		
	}
	
	private void buildReaders() {
		System.out.println(bookGrid.getSelectedItems().size());
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
