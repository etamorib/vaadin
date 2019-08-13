package de.cas.vaadin.thelibrary.ui.view.content;

import java.time.Year;
import java.util.ArrayList;

import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import org.vaadin.alump.fancylayouts.FancyCssLayout;
import org.vaadin.ui.NumberField;

import com.vaadin.data.Binder;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


/**
 * @author mate.biro
 * This is the GUI of the BooksView
 * TODO: It could be simplified with better design
 */
public class BooksView implements CreateContent{
	private HorizontalLayout mainLayout;
	private VerticalLayout left;
	private FancyCssLayout editLayout = new FancyCssLayout();
	private final String name ="Books";
	private Grid<Book> grid =  new Grid<>(Book.class);
	private BookController controller = new BookController();
	private Button add, del, edit;
	private ListDataProvider<Book> dataProvider ;
	
	
	/**
	 * Builds the content of the view
	 * @return The complete layout of this view
	 */
	@Override
	public Component buildContent() {
		//The main layout
		mainLayout = new HorizontalLayout();
		mainLayout.removeStyleName("no-background");
		mainLayout.setSizeFull();
		
		//Sub layout(it will be on the left side)
		left = new VerticalLayout();
		
		Label title = new Label("Books in the database");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		//Adding components to the "left" side
		left.addComponents(title,buildButtons(), buildGrid());
		mainLayout.addComponent(left);
		return mainLayout;
	}
	//This method builds the grid of this view, containing Books
	private Component buildGrid() {
		//Dataprovider which gets the data from the database
		dataProvider = new ListDataProvider<>(controller.getItems());
		
		//To make sure column order
		grid.setColumns("author","title", "id", "year", "state");
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		
		//Selector for filtering by state
		NativeSelect<BookState> state = new NativeSelect<>();
		state.setStyleName("dropdown-select");
		state.setItems(BookState.Available, BookState.Borrowed, BookState.Deleted);
		state.addSelectionListener(e->{
			//If there is something selected
			if(e.getValue()!=null) {
				dataProvider.setFilter(book -> book.getState() ==e.getValue());	
			}
			//Else just show everything
			else {
				dataProvider.clearFilters();
			}	
		});
		//Adding the selector
		grid.addHeaderRowAt(1);
		grid.getHeaderRow(1).getCell("state").setComponent(state);
		
		//Drag grid
		GridDragSource<Book> source = new GridDragSource<Book>(grid);
		source.setEffectAllowed(EffectAllowed.MOVE);	
		
		//Drop elements to the delete or edit button
		DropTargetExtension<Button> dropDeleteTarget = new DropTargetExtension<>(del);
		DropTargetExtension<Button> dropEditTarget = new DropTargetExtension<Button>(edit);
		dropDeleteTarget.setDropEffect(DropEffect.MOVE);
		dropEditTarget.setDropEffect(DropEffect.MOVE);
		dropDeleteTarget.addDropListener(e->{
			//If deleting is successful
			if(controller.delete(grid.getSelectedItems())) {
				new Notification("",
					    "Book(s) have been deleted!",
					    Notification.Type.WARNING_MESSAGE, true)
					    .show(Page.getCurrent());
				//Refresh the grid
				dataProvider = new ListDataProvider<>(controller.getItems());
				grid.setDataProvider(dataProvider);
				grid.deselectAll();
			}
		});
		//Drop to the edit button
		dropEditTarget.addDropListener(e->{
			//If there is at least 1 item selected
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				//For every book, a new layout is added
				for(Book b: grid.getSelectedItems()) {
					buildEditLayout(editLayout,  b);
				}
				mainLayout.addComponent(editLayout);
			}
			//Else just show a message
			else {
				Notification.show("There is nothing to be edited!");
			}
		});
		grid.setSizeFull();
		return grid;
	}
	//Building the editing layout on the right side
	private void buildEditLayout(final FancyCssLayout editLayout, Book b) {
		
			final FormLayout editForm = new FormLayout();
			
			//Buttons
			//Save
			Button save = new Button("Save");
			save.setStyleName(ValoTheme.BUTTON_PRIMARY);
			//Cancel
			Button cancel = new Button("Cancel");
			cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
			cancel.addClickListener(e->{
				//If there are components on the layout, lets just remove
				if(editLayout.getComponentCount()>1) {
					editLayout.fancyRemoveComponent(editForm);
							
				}
				//If there are no more components, remove the whole layout
				else {	
					editLayout.removeAllComponents();
					grid.setEnabled(true);
					mainLayout.removeComponent(editLayout);
					//Reset grid and its size
					grid.deselectAll();
					grid.setSizeFull();
				}
				
			});
			
			
			//Form
			Binder<Book> binder = new Binder<>();
			TextField title = new TextField("Title");
			title.setValue(b.getTitle());
			TextField author = new TextField("Author");
			author.setValue(b.getAuthor());
			
			//Select the state
			NativeSelect<BookState> state = new NativeSelect<>();
			state.setCaption("State");
			state.setValue(b.getState());
			state.setStyleName("dropdown-select");
			
			//Select the year
			NativeSelect<Integer> selectYear = new NativeSelect<Integer>();
			selectYear.setCaption("Year");
			selectYear.setValue(b.getYear());
			selectYear.setStyleName("dropdown-select");
			
			//If book is borrowed, state cannot be changed
			if(b.getState()==BookState.Borrowed) {
				state.setEnabled(false);
			}
			//Setting the values of the selector
			state.setItems(BookState.Available, BookState.Deleted);
			ArrayList<Integer> years = new ArrayList<Integer>();
			for(int i=1700; i<=Year.now().getValue(); i++) {
				years.add(i);
			}
			selectYear.setItems(years);
			
			
			NumberField id = new NumberField("id");
			id.setEnabled(false);
			id.setValue(b.getId().toString());
			binder.bind(title, Book::getTitle, Book::setTitle);
			binder.bind(author, Book::getAuthor, Book::setAuthor);
			binder.bind(selectYear, Book::getYear, Book::setYear);
			binder.bind(state, Book::getState, Book::setState);
			
			//Save button
			save.addClickListener(e->{
				//Create new title based on values of fields
				Book book = new Book(title.getValue(), author.getValue(),
										Integer.parseInt(id.getValue()), selectYear.getValue(), state.getValue());
				//Check if there are components left on the layout
				if(editLayout.getComponentCount()>1) {
					//Update book in database
					if(controller.update(book)) {
						Notification.show("Update successful");
						//Reset grid
						dataProvider = new ListDataProvider<>(controller.getItems());
						grid.setDataProvider(dataProvider);
						editLayout.fancyRemoveComponent(editForm);
						
					}
					else {
						Notification.show("Something went wrong!");
						editLayout.fancyRemoveComponent(editForm);
					}
				}
				//If there are no more components, remove this layout and reset grid 
				else {
					if(controller.update(book)) {
						Notification.show("Update successful");
					}else {
						Notification.show("Something went wrong");
					}
					grid.setEnabled(true);
					editLayout.removeAllComponents();
					mainLayout.removeComponent(editLayout);
					grid.setSizeFull();
					grid.deselectAll();
					dataProvider = new ListDataProvider<>(controller.getItems());
					grid.setDataProvider(dataProvider);
				}
				
			});
			
			editForm.setSizeFull();
			editForm.addComponents(title, author, state, selectYear, id, save, cancel);
			editLayout.addComponent(editForm);
		
	}
	//Making the add, delete, update buttons and a search field
	private Component buildButtons() {
		HorizontalLayout buttons = new HorizontalLayout();
		//Add Button
		add = new Button();
		add.setIcon(VaadinIcons.PLUS);
		add.setStyleName("header-button");
		add.setDescription("Add new book to database");
		add.addClickListener(e->{
			addingWindow();
		});
		
		//Del button
		del = new Button();
		del.setIcon(VaadinIcons.TRASH);
		del.setStyleName("header-button");
		del.setDescription("Delete selected items");
		del.addClickListener(e->{
			boolean flag = false;
			if(grid.getSelectedItems().size()>0) {
				//Delete selected items, and update grid
				for(Book b: grid.getSelectedItems()) {
					for(Rent r : MasterController.getRentController().getItems()){
						if(r.getBookId().intValue() == b.getId().intValue()){
							flag=true;
						}
					}
					if(flag){
						Notification.show("Book cannot be deleted while it is rented");
					}else{
						System.out.println("TRUE");
						controller.delete(b);
						dataProvider = new ListDataProvider<>(controller.getItems());
						grid.setDataProvider(dataProvider);
					}
					flag=false;

				}
			}
			else {
				Notification.show("There is nothing to be deleted!");
			}

		});
		
		//Edit button
		edit = new Button();
		edit.setIcon(VaadinIcons.PENCIL);
		edit.setStyleName("header-button");
		edit.setDescription("Edit selected items");
		edit.addClickListener(e->{
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				//For every book make a new layout
				for(Book b: grid.getSelectedItems()) {
					buildEditLayout(editLayout,  b);
				}
				mainLayout.addComponent(editLayout);
			}
			else {
				Notification.show("There is nothing to be edited!");
			}
			
		});
		
		//Search field to filter
		TextField search  = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setPlaceholder("Search...");
		search.setValueChangeMode(ValueChangeMode.EAGER);
		search.addValueChangeListener(e->{
			//Filter for both title and author
			dataProvider.setFilter(book -> book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
					book.getTitle().toLowerCase().contains(e.getValue().toLowerCase()));
			
			
		});
		buttons.addComponents(add, del, edit, search);
		return buttons;
	}

	//Popup window for adding new Book to the database
	private void addingWindow() {
		Window window = new Window();
		window.setCaption("Add new book");
		FormLayout form = new FormLayout();
		form.setStyleName("black-text");
		
		//author
		TextField author = new TextField("Author");
		author.setIcon(VaadinIcons.PENCIL);
		author.setRequiredIndicatorVisible(true);
			
		//title
		TextField title = new TextField("Title");
		title.setIcon(VaadinIcons.TEXT_LABEL);
		title.setRequiredIndicatorVisible(true);
		
		//ID
		NumberField id = new NumberField("Id");
		id.setStyleName("dropdown-select");
		id.setIcon(VaadinIcons.EXCLAMATION);
		id.setRequiredIndicatorVisible(true);
		id.setNegativeAllowed(false);
		id.setGroupingUsed(false);
		//Year
		NumberField year = new NumberField("Year");
		year.addStyleName("dropdown-select");
		year.setIcon(VaadinIcons.CALENDAR);
		year.setRequiredIndicatorVisible(true);
		year.setNegativeAllowed(false);
		year.setGroupingUsed(false);
		
		//Add button
		Button add = new Button("Add");
		add.setStyleName(ValoTheme.BUTTON_PRIMARY);
		add.setClickShortcut(KeyCode.ENTER);
		add.setIcon(VaadinIcons.PLUS);
		add.addClickListener(e->{
			Book b = new Book(title.getValue(), author.getValue(), Integer.parseInt(id.getValue()), 
								Integer.parseInt(year.getValue()), BookState.Available);
			if(controller.add(b)) {
				window.close();
				Notification.show("Book has been added to database");
				dataProvider = new ListDataProvider<>(controller.getItems());
				grid.setDataProvider(dataProvider);
				
			}
		});
		
		form.addComponents(author, title, id, year, add);
		form.setSizeUndefined();
		
		window.setContent(form);
		window.addStyleName("add-window");
		window.setSizeUndefined();
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

	@Override
	public Resource menuIcon() {
		return VaadinIcons.BOOK;
	}


}