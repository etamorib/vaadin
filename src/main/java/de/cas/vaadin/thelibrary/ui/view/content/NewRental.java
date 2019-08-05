package de.cas.vaadin.thelibrary.ui.view.content;

import java.util.Set;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;

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

	
	
	@Override
	public Component buildContent() {
		mainLayout = new VerticalLayout();
		tab = new TabSheet();
		tab.setSizeFull();
		books = new VerticalLayout();
		books.addComponent(booksLayout());
		tab.addTab(books).setIcon(VaadinIcons.BOOK);
		readers = new VerticalLayout(new Label("Readers"));
		tab.addTab(readers).setIcon(VaadinIcons.USERS);
		deadline = new VerticalLayout(new Label("Deadline"));
		tab.addTab(deadline).setIcon(VaadinIcons.CALENDAR_CLOCK);
		tab.setResponsive(true);
		mainLayout.addComponent(tab);
		return mainLayout;
	}
	
	private Component booksLayout() {
		books = new VerticalLayout();
		Label counter = new Label();
		//Grid
		Grid<Book> bookGrid = new Grid<>(Book.class);
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
		
		//
		bookGrid.addSelectionListener(e->{
			int currentlySelected = bookGrid.getSelectedItems().size();
			
			updateCounter(counter, currentlySelected);
			if(currentlySelected> maxSelect) {
				bookGrid.deselect(bookGrid.getSelectedItems().iterator().next());
			}
		});
		
		bookGrid.addHeaderRowAt(1);
		bookGrid.getHeaderRow(1).getCell("state").setComponent(state);
		
		
		books.addComponent(bookGrid);
		books.addComponent(counter);
		return books;
	}
	
	private void updateCounter(Label l, int i) {
		l.setCaption(i+" items are selected, "+ (maxSelect-i)+" remaining.");
		
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
