package de.cas.vaadin.thelibrary.ui.view.content;

import java.time.LocalDate;
import java.util.ArrayList;

import org.vaadin.alump.fancylayouts.FancyCssLayout;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.ReaderController;
import de.cas.vaadin.thelibrary.controller.RentController;
import de.cas.vaadin.thelibrary.controller.WaitlistController;
import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

/**This is the view of new rentals.
 * It consists of 3 stages: Book selection, reader selection and borrowing / adding to waitlist
 * @author mate.biro
 *
 */
public class NewRental implements CreateContent {
	
	private RentController rentController = new RentController();
	private BookController bookController = new BookController();
	private WaitlistController waitlistController = new WaitlistController();
	
	private final int maxSelect = 5;
	private final String name = "New Rentals";
	private VerticalLayout mainLayout;
	private TabSheet tab;
	private VerticalLayout books, readers, deadline;
	private FancyCssLayout left ;
	private ReaderController readerController = new ReaderController();
	private ListDataProvider<Book> bookDataProvider ;
	private ListDataProvider<Reader> readerDataProvider;
	private ListDataProvider<Book> borrowedBookProvider;
	private Reader selectedReader = null;
	private Grid<Book> bookGrid;
	private Grid<Reader> readerGrid;
	private ListSelect<Book> list = null;
	
	public NewRental() {
		AppEventBus.register(this);
	}
	
	
	@Override
	public Component buildContent() {
		Label title = new Label("Add new rentals");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		//The main layout
		mainLayout = new VerticalLayout(title);
		
		//Tab for different stages
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
	/*Tabs are built in the beginning
	//This method gives a little dynamic to them, so they refresh
	every time we change tab
	*/
	private void updateLayouts() {
		readers.removeAllComponents();
		readers.addComponent(readersLayout());
		
		deadline.removeAllComponents();
		deadline.addComponent(deadlineLayout());
		
	}
	
	//Build a form for the reader who wants to rent
	private ComponentContainer currentReaderForm(Reader reader, boolean available) {
		VerticalLayout layout = new VerticalLayout();
		layout.setCaption("Renter");
		
		//All the neccessary labels
		Label name, address, email, tel, id, deadline;
		name = new Label("Name: "+reader.getName());
		address = new Label("Address: "+reader.getAddress());
		email = new Label("Email: "+reader.getEmail());
		tel = new Label("Phone number: "+reader.getPhoneNumber().toString());
		id = new Label("ID: "+reader.getId().toString());
		deadline = new Label("Books can be rented until: "+ LocalDate.now().plusMonths(2).toString());
		
		deadline.setStyleName(ValoTheme.LABEL_BOLD);
		layout.addComponents(name, address, email, tel, id);
		
		//Available tells if there were any available books selected
		//If so, a deadline date is also shown
		if(available) {
			layout.addComponent(deadline);
		}
		return layout;
	}
	
	//DEADLINE TAB
	private Component deadlineLayout() {
		HorizontalLayout deadline = new HorizontalLayout();
		left = new FancyCssLayout();
		left.setSizeFull();
		deadline.setSizeFull();
		Grid<Book> availableGrid = new Grid<>(Book.class);
		Grid<Book> borrowedGrid = new Grid<>(Book.class);
		availableGrid.setStyleName("grid-overall");
		borrowedGrid.setStyleName("grid-overall");
		
		//Labels for grid titles:
		Label ava = new Label("Currently available books - rent");
		Label borr = new Label("Currently borrowed books - to waitlist");

		//Containers for available books and borrowed books
		ArrayList<Book> availableList = new ArrayList<Book>();
		ArrayList<Book> borrowedList = new ArrayList<Book>();
		for(Book b: bookGrid.getSelectedItems()) {
			if(b.getState() ==BookState.Available) {
				availableList.add(b);
			}
			if(b.getState() == BookState.Borrowed) {
				borrowedList.add(b);
			}
		}
		
		//Grid for available books
		availableGrid.setItems(availableList);
		availableGrid.setSizeFull();
		
		//Grid for borrowed books
		borrowedBookProvider = new ListDataProvider<>(borrowedList);
		borrowedGrid.setDataProvider(borrowedBookProvider);
		borrowedGrid.setSizeFull();
		
		if(availableList.size()>0) {
			
			Button rent = new Button("Borrow");
			rent.addStyleName("header-button");
			rent.setSizeFull();
			rent.addClickListener(e->{
				//Adds rent to database and removes components from layout
				boolean error = false;
				left.fancyRemoveComponent(ava);
				left.fancyRemoveComponent(availableGrid);
				left.fancyRemoveComponent(rent);
				for(Book b: availableList) {
					//Checks if there is error while adding to rent database
					if(!rentController.add(new Rent(LocalDate.now(), LocalDate.now().plusMonths(2), b.getId(), selectedReader.getId()))) {
						error = true;
					}
					//If there was no error it updates the database (sets book to borrowed)
					else {
						b.setState(BookState.Borrowed);
						bookController.update(b);
						bookGrid.deselect(b);
					}
					
				}
				if(error) {
					Notification.show("ERROR");
				}
				else {
					Notification.show("SUCCESS");
				}
				//If no more components left, it sends back to the beginning of the view
				if(left.getComponentCount()==3) {
					AppEventBus.post(new ChangeViewEvent(new NewRental()));
				}
			});
			left.addComponents(ava, availableGrid, rent);
		}
		//If there was any borrowed books selected
		if(borrowedList.size()>0) {
			Button wl = new Button("Add to waitlist");
			wl.setStyleName("header-button");
			wl.setSizeFull();
			wl.addClickListener(e->{
				//Creates a popup window
				waitlistWindow(borrowedList, borr, borrowedGrid, wl);
			});
			left.addComponents(borr, borrowedGrid, wl);
		}
		//Calls the current reader form and with the right boolean parameter
		//to show deadline date
		deadline.addComponents(left, selectedReader!=null?availableList.size()>0? new VerticalLayout(currentReaderForm(selectedReader, true)):
								new VerticalLayout(currentReaderForm(selectedReader, false)):new Label("no user"));
		
		
		return deadline;
	}
	//Popup window to set date
	private void waitlistWindow(ArrayList<Book> borrowedBooks, Label borr, Grid<Book> borrowedGrid, Button wl) {
		Window window = new Window();
		window.setCaption("Set date");
		
		
		HorizontalLayout layout = new HorizontalLayout();
		
		for(Book b: borrowedBooks) {
			//Form for the book's data
			FormLayout form = new FormLayout();
			//Author
			TextField author = new TextField("Author");
			author.setIcon(VaadinIcons.PENCIL);
			author.setValue(b.getAuthor());
			author.setEnabled(false);
					
			//title
			TextField title = new TextField("Title");
			title.setIcon(VaadinIcons.TEXT_LABEL);
			title.setValue(b.getTitle());
			title.setEnabled(false);

			
			TextField year = new TextField("Year");
			year.setIcon(VaadinIcons.CALENDAR);
			year.setValue(b.getYear().toString());
			year.setEnabled(false);
			
			TextField rentedUntil = new TextField("Rented until");
			year.setIcon(VaadinIcons.CALENDAR_USER);
			//Sets the time when the book will be returned
			rentedUntil.setValue(rentController.getRentByBookId(b.getId()).getReturnTime().toString());
			rentedUntil.setEnabled(false);
			DateField dateField = new DateField();
			//DateFields starts from the return date
			dateField.setValue(rentController.getRentByBookId(b.getId()).getReturnTime());
			Button add = new Button("Set date");
			add.addClickListener(e->{
				//If adding to waitlist database is not successful
				if(!waitlistController.add(new Waitlist(b.getId(), 
						selectedReader.getId(), LocalDate.now(), dateField.getValue()))) {
					Notification.show("Something went wrong!");
				}
				//If it was successful
				else {
					Notification.show("Request added to waitlist");
				}
				
				//Logic of removing components based on their number
				//and refreshing the view
				layout.removeComponent(form);
				borrowedBooks.remove(b);
				borrowedBookProvider = new ListDataProvider<>(borrowedBooks);
				borrowedGrid.setDataProvider(borrowedBookProvider);
				if(layout.getComponentCount()==0) {
					left.fancyRemoveComponent(borr);
					left.fancyRemoveComponent(borrowedGrid);
					left.fancyRemoveComponent(wl);
					window.close();
					Notification.show("Books have been added to waitlist");
					
					if(left.getComponentCount()==3) {
						AppEventBus.post(new ChangeViewEvent(new NewRental()));
					}
				}
				
			});
			
			form.addComponents(author, title, year, rentedUntil, dateField ,add);
			layout.addComponent(form);
		}
		window.setContent(layout);
		window.addStyleName("add-window");
		window.setResizable(false);
		window.center();
		window.setDraggable(false);
		window.setModal(true);
		//Show the window		
		UI.getCurrent().addWindow(window);
	
	}
	
	//TAB OF BOOK SELECTION
	private Component booksLayout() {
		//Returned main layout
		VerticalLayout books = new VerticalLayout();
		
		Label title = new Label("Select books that want to be rented ");
		title.setStyleName(ValoTheme.LABEL_H2);
		
		//Counter for selected items
		Label counter = new Label();
		
		//Button for adding
		Button add = new Button("Select books");
		add.setStyleName("header-button");
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
		//Filter for state
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
			//If there are more than 5 selections, it deselects the first, then second ...
			if(currentlySelected> maxSelect) {
				bookGrid.deselect(bookGrid.getSelectedItems().iterator().next());
			}
			if(currentlySelected > 0) {
				//Adding selected items to the ListSelect to show on next tab
				list = new ListSelect<>();
				list.addStyleName("white-text");
				list.setItems(bookGrid.getSelectedItems());
				list.setSizeFull();
	
				//Go to next tab
				add.setEnabled(true);
				add.addClickListener(event->{
					tab.getTab(readers).setEnabled(true);
					tab.setSelectedTab(readers);

				});
			}
			//If books are deselected further tabs are disabled
			else {
				tab.getTab(readers).setEnabled(false);
				add.setEnabled(false);
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
			bookDataProvider.setFilter(book -> (book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
					book.getTitle().toLowerCase().contains(e.getValue().toLowerCase())) &&
					book.getState()!=BookState.Deleted);
					
		});
		
		books.addComponents(title, search, bookGrid, counter, add);
		return books;
	}
	
	//Updates the counter for selected books
	private void updateCounter(Label l, int i) {
		l.setCaption(i+" items are selected, "+ (maxSelect-i)+" remaining.");
		
	}
	
	/*BUILDING READERS TAB*/
	
	private Component readersLayout() {
		//Returned main layout
		VerticalLayout readers = new VerticalLayout();
		
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
		add.setStyleName("header-button");
		add.setEnabled(false);
		
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
				
				selectedReader = readerGrid.getSelectedItems().iterator().next();
				add.setEnabled(true);
				add.addClickListener(event->{
					tab.getTab(deadline).setEnabled(true);
					tab.setSelectedTab(deadline);
				});
			}
			else {
				
				tab.getTab(deadline).setEnabled(false);
				add.setEnabled(false);
			}
		});

		
		//Searchfield
		TextField search  = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setPlaceholder("Find a reader...");
		search.setValueChangeMode(ValueChangeMode.EAGER);
		search.addValueChangeListener(e->{
			//Filter for both title or author or email or phone
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
