package de.cas.vaadin.thelibrary.ui.view.content;



import java.time.Year;
import java.util.ArrayList;
import java.util.Set;

import com.vaadin.data.Binder;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Book;
import de.cas.vaadin.thelibrary.bean.BookState;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


public class BooksView implements CreateContent{
	HorizontalLayout layout ;
	VerticalLayout subLayout ;
	private final String name ="Books";
	private Grid<Book> grid =  new Grid<>(Book.class);
	VerticalLayout editLayout = new VerticalLayout();
	Panel panel = new Panel();
	private Book dragged = null;
	Label label = new Label("SAJT");
	Button addNew = new Button();
	Button del = new Button();
	Button edit = new Button();
	

	public BooksView() {
		
	}

	@Override
	public Component buildContent() {
		
		layout = new HorizontalLayout();
		subLayout = new VerticalLayout();
		
		subLayout.setSizeFull();
		layout.setSizeFull();
		editLayout.setSizeFull();
		panel.setSizeFull();
		
		
		

		subLayout.addComponents(buildButtonsMenu(), buildGrid());
		/*layout.addComponent(buildButtonsMenu());
		layout.addComponent(grid);*/
		layout.addComponent(subLayout);
		return layout;
	}

	private Component buildGrid() {
		//Grid to show data
		
		grid.setStyleName("grid-overall");
		
		
		grid.setColumns("title", "author", "id", "year", "state");
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.getEditor().setEnabled(true);
		grid.getEditor().setBuffered(true);
		
		//Drag grid
		GridDragSource<Book> source = new GridDragSource<Book>(grid);
		source.setEffectAllowed(EffectAllowed.MOVE);
		source.addGridDragStartListener(e->{
			dragged = e.getDraggedItems().iterator().next();
		});
		source.addGridDragEndListener(e->{
			dragged = null;
		});
		
		//Drop grid
		
		// make the del button accept drops
		DropTargetExtension<Button> dropTarget = new DropTargetExtension<>(del);
		dropTarget.setDropEffect(DropEffect.MOVE);
		
		//TODO: adatbazisban megoldani
		dropTarget.addDropListener(e->{
			new Notification("",
				    "Book with id <strong>"+ dragged.getId()+"</strong> has been deleted",
				    Notification.Type.WARNING_MESSAGE, true)
				    .show(Page.getCurrent());
			dragged.setState(BookState.Deleted);
			grid.getDataProvider().refreshAll();
		});
			
		
		//TODO: use database instead
		grid.setItems(new Book("sajt", "Lajos",123123l, 1994, BookState.Available),
				new Book("asdasd", "Peter",412123l, 2000, BookState.Available),
				new Book("history of cheese", "Cheezy",566123l, 1667, BookState.Borrowed));

		//Edit grid
		grid.getColumn("title").setEditorComponent(new TextField("Title"));
		grid.getColumn("author").setEditorComponent(new TextField("Author"));
		//grid.getColumn("id").setEditorComponent(new TextField("ID"));
		NativeSelect<Integer> selectYear = new NativeSelect<Integer>();
		ArrayList<Integer> years = new ArrayList<Integer>();
		for(int i=1700; i<=Year.now().getValue(); i++) {
			years.add(i);
		}
		selectYear.setItems(years);
		grid.getColumn("year").setEditorComponent(selectYear);
		NativeSelect<BookState> states = new NativeSelect<>();
		states.setItems(BookState.Available, BookState.Borrowed, BookState.Deleted);
		grid.getColumn("state").setEditorComponent(states);
		grid.getEditor().addSaveListener(listener ->{
			
			//listener.getBean.getId alapjan lehet modositani adatbazisba
			System.out.println(listener.getBean().getTitle());
			//Bean object: ezt kell beadnom az adatbazis updatebe
			System.out.println(listener.getBean());		
			
		});
		
		
		//TODO: igy lehet megnezni mennyi van kivalasztva
		//TODO: ez csak egy teszt, majd mashova kell
		grid.addSelectionListener(listener->{
			if(	grid.getSelectedItems().size() >0) {
				System.out.println("NAGYOBB");	
				
			}
			
		});
		
				
		grid.setSizeFull();
		return grid;
	}
	
	private Component buildButtonsMenu() {
		HorizontalLayout headerButtons = new HorizontalLayout();
		//Add button
		addNew.setIcon(VaadinIcons.PLUS);
		addNew.setDescription("Add new book to database");
		//TODO: add clicklistener to button
		addNew.addClickListener(e->{
			addNewPopup();
		});
		
		//Del button
		//TODO addClick listener to update status of books
		del.setIcon(VaadinIcons.TRASH);
		del.setDescription("Delete selected by pressing or drag items");
		
		//Edit button
		edit.setIcon(VaadinIcons.PENCIL);
		edit.setDescription("Edit selected items");
		edit.addClickListener(e->{
			
			int selectedItems = grid.getSelectedItems().size();
			if(selectedItems>0) {
				layout.addComponent(buildEditLayout(grid.getSelectedItems()));
				grid.setEnabled(false);
			}
			
		});
		headerButtons.addComponents(addNew, del, edit);

		return headerButtons;
		
	}

	private Component buildEditLayout(Set<Book> selectedItems) {
		Label formTitle = new Label("Edit book(s)");
		formTitle.setStyleName(ValoTheme.LABEL_H2);
		editLayout.addComponent(formTitle);
		for(Book b : selectedItems) {
			editLayout.addComponent(buildEditBookForm(b));
		}

		panel.setContent(editLayout);
		
		return panel;
		
	}

	private Component buildEditBookForm(Book b) {
		
		Button save = new Button("Save");
		save.addClickListener(e->{
			grid.setEnabled(true);
			editLayout.removeAllComponents();
			
		});
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		Button cancel = new Button("Cancel");
		cancel.addClickListener(e->{
			grid.setEnabled(true);
			editLayout.removeAllComponents();
			grid.setSizeFull();
			subLayout.removeAllComponents();
			subLayout.addComponents(buildButtonsMenu(), grid);
			/*layout.addComponent(buildButtonsMenu());
			layout.addComponent(grid);*/
			layout.removeAllComponents();
			layout.addComponent(subLayout);
			
		});
		cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
		
		
		
		
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
		id.setValue(b.getId().toString());
		binder.bind(title, Book::getTitle, Book::setTitle);
		binder.bind(author, Book::getAuthor, Book::setAuthor);
		binder.bind(selectYear, Book::getYear, Book::setYear);
		binder.bind(state, Book::getState, Book::setState);
		

		FormLayout layout = new FormLayout();
		layout.setSizeFull();
		layout.addComponents(title, author, state, selectYear, id, save, cancel);
		
		return layout;
	}

	//TODO : finish form, add clicklistener to Add button
	private void addNewPopup() {
		Window window = new Window();
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
		add.setIcon(VaadinIcons.PLUS);
		
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
	
	

}