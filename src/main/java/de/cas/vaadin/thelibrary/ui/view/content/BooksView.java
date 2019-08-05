package de.cas.vaadin.thelibrary.ui.view.content;



import java.time.Year;
import java.util.ArrayList;

import org.vaadin.alump.fancylayouts.FancyCssLayout;

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


public class BooksView implements CreateContent{
	private HorizontalLayout mainLayout;
	private VerticalLayout left;
	private FancyCssLayout editLayout = new FancyCssLayout();
	private final String name ="Books";
	private Grid<Book> grid =  new Grid<>(Book.class);
	private BookController controller = new BookController();
	private Button add, del, edit;
	private ListDataProvider<Book> dataProvider ;
	
	
	@Override
	public Component buildContent() {
		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		left = new VerticalLayout();
		Label title = new Label("Books in the database");
		title.setStyleName(ValoTheme.LABEL_H1);
		left.addComponents(title,buildButtons(), buildGrid());
		mainLayout.addComponent(left);
		return mainLayout;
	}
	
	private Component buildGrid() {
		dataProvider = new ListDataProvider<>(controller.getItems());
		//To make sure column order
		grid.setColumns("author","title", "id", "year", "state");
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		NativeSelect<BookState> state = new NativeSelect<>();
		state.setItems(BookState.Available, BookState.Borrowed, BookState.Deleted);
		state.addSelectionListener(e->{
			if(e.getValue()!=null) {
				dataProvider.setFilter(book -> book.getState() ==e.getValue());
			}else {
				dataProvider.clearFilters();
			}
			
		});
		
		grid.addHeaderRowAt(1);
		grid.getHeaderRow(1).getCell("state").setComponent(state);
		//Drag grid
		GridDragSource<Book> source = new GridDragSource<Book>(grid);
		source.setEffectAllowed(EffectAllowed.MOVE);	
		
		//Drop grid
		DropTargetExtension<Button> dropDeleteTarget = new DropTargetExtension<>(del);
		DropTargetExtension<Button> dropEditTarget = new DropTargetExtension<Button>(edit);
		dropDeleteTarget.setDropEffect(DropEffect.MOVE);
		dropEditTarget.setDropEffect(DropEffect.MOVE);
		dropDeleteTarget.addDropListener(e->{
			if(controller.delete(grid.getSelectedItems())) {
				new Notification("",
					    "Book(s) have been deleted!",
					    Notification.Type.WARNING_MESSAGE, true)
					    .show(Page.getCurrent());
				grid.setItems(controller.getItems());
				grid.deselectAll();
			}
		});
		
		dropEditTarget.addDropListener(e->{
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				for(Book b: grid.getSelectedItems()) {
					buildEditLayout(editLayout,  b);
				}
				mainLayout.addComponent(editLayout);
			}else {
				Notification.show("There is nothing to be edited!");
			}
		});
		grid.setSizeFull();
		return grid;
	}
	
	private void buildEditLayout(final FancyCssLayout editLayout, Book b) {
		
			final FormLayout editForm = new FormLayout();
			
			//Buttons
			Button save = new Button("Save");
			save.setStyleName(ValoTheme.BUTTON_PRIMARY);
			Button cancel = new Button("Cancel");
			cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
			cancel.addClickListener(e->{
				if(editLayout.getComponentCount()>1) {
					editLayout.fancyRemoveComponent(editForm);
							
				}else {	
					editLayout.removeAllComponents();
					grid.setEnabled(true);
					mainLayout.removeComponent(editLayout);
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
			NativeSelect<BookState> state = new NativeSelect<>();
			state.setCaption("State");
			state.setValue(b.getState());
			NativeSelect<Integer> selectYear = new NativeSelect<Integer>();
			selectYear.setCaption("Year");
			selectYear.setValue(b.getYear());
			state.setItems(BookState.Available, BookState.Borrowed, BookState.Deleted);
			ArrayList<Integer> years = new ArrayList<Integer>();
			for(int i=1700; i<=Year.now().getValue(); i++) {
				years.add(i);
			}
			selectYear.setItems(years);
			
			//TODO: NumberField
			TextField id = new TextField("id");
			id.setEnabled(false);
			id.setValue(b.getId().toString());
			binder.bind(title, Book::getTitle, Book::setTitle);
			binder.bind(author, Book::getAuthor, Book::setAuthor);
			binder.bind(selectYear, Book::getYear, Book::setYear);
			binder.bind(state, Book::getState, Book::setState);
			
			//Save button
			save.addClickListener(e->{
				Book book = new Book(title.getValue(), author.getValue(),
										Integer.parseInt(id.getValue()), selectYear.getValue(), state.getValue());
				if(editLayout.getComponentCount()>1) {
					if(controller.update(book)) {
						Notification.show("Update successful");
						dataProvider = new ListDataProvider<>(controller.getItems());
						grid.setDataProvider(dataProvider);
						editLayout.fancyRemoveComponent(editForm);
						
					}else {
						Notification.show("Something went wrong!");
						editLayout.fancyRemoveComponent(editForm);
					}
				}else {
					if(controller.update(new Book(title.getValue(), author.getValue(),
										Integer.parseInt(id.getValue()), selectYear.getValue(), state.getValue()))) {
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

	private Component buildButtons() {
		HorizontalLayout buttons = new HorizontalLayout();
		//Add Button
		add = new Button();
		add.setIcon(VaadinIcons.PLUS);
		add.setDescription("Add new book to database");
		add.addClickListener(e->{
			addingWindow();
		});
		
		//Del button
		del = new Button();
		del.setIcon(VaadinIcons.TRASH);
		del.setDescription("Delete selected items");
		del.addClickListener(e->{
			controller.delete(grid.getSelectedItems());
			dataProvider = new ListDataProvider<>(controller.getItems());
			grid.setDataProvider(dataProvider);

		});
		
		//Edit button
		edit = new Button();
		edit.setIcon(VaadinIcons.PENCIL);
		edit.setDescription("Edit selected items");
		edit.addClickListener(e->{
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				for(Book b: grid.getSelectedItems()) {
					buildEditLayout(editLayout,  b);
				}
				mainLayout.addComponent(editLayout);
			}else {
				Notification.show("There is nothing to be edited!");
			}
			
		});
		
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

	
	private void addingWindow() {
		Window window = new Window();
		window.setCaption("Add new book");
		//VerticalLayout layout = new VerticalLayout();
		//layout.addComponents(new Label("Title:"),new TextField(), new Button("Add"));
		FormLayout form = new FormLayout();
		
		//author
		TextField author = new TextField("Author");
		author.setIcon(VaadinIcons.PENCIL);
		author.setRequiredIndicatorVisible(true);
		form.addComponent(author);
		
		//title
		TextField title = new TextField("Title");
		title.setIcon(VaadinIcons.TEXT_LABEL);
		title.setRequiredIndicatorVisible(true);
		
		//ID
		//TODO: NumberField jobb lenne
		TextField id = new TextField("Id");
		id.setIcon(VaadinIcons.EXCLAMATION);
		id.setRequiredIndicatorVisible(true);
		//Year
		//TODO: Select kéne
		TextField year = new TextField("Year");
		year.setIcon(VaadinIcons.CALENDAR);
		year.setRequiredIndicatorVisible(true);
		
		Button add = new Button("Add");
		add.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		add.setClickShortcut(KeyCode.ENTER);
		add.setIcon(VaadinIcons.PLUS);
		add.addClickListener(e->{
			Book b = new Book(title.getValue(), author.getValue(), Integer.parseInt(id.getValue()), 
								Integer.parseInt(year.getValue()), BookState.Available);
			if(controller.add(b)) {
				window.close();
				Notification.show("Book has been added to database");
				grid.setItems(controller.getItems());
				
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