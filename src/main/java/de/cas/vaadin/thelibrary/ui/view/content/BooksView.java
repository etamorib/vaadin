package de.cas.vaadin.thelibrary.ui.view.content;



import java.time.Year;
import java.util.ArrayList;
import java.util.Set;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.dnd.DropEffect;
import com.vaadin.shared.ui.dnd.EffectAllowed;
import com.vaadin.shared.ui.grid.DropMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.components.grid.GridDropTarget;
import com.vaadin.ui.dnd.DragSourceExtension;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Book;
import de.cas.vaadin.thelibrary.bean.BookState;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


public class BooksView implements CreateContent{
	private final String name ="Books";
	private Grid<Book> grid;
	private Book dragged = null;
	

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
		
		VerticalLayout dropTargetLayout = new VerticalLayout();
		dropTargetLayout.setCaption("Drop to delete");
		dropTargetLayout.addStyleName("drag-layout");
		dropTargetLayout.setWidth("40px");
		dropTargetLayout.setIcon(VaadinIcons.CLOSE_CIRCLE_O);

		// make the layout accept drops
		DropTargetExtension<VerticalLayout> dropTarget = new DropTargetExtension<>(dropTargetLayout);
		dropTarget.setDropEffect(DropEffect.MOVE);
		layout.addComponent(dropTargetLayout);
		
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
		grid.setItems(new Book("sajt", "Lajos",123123, 1994, BookState.Available),
				new Book("asdasd", "Peter",412123, 2000, BookState.Available),
				new Book("history of cheese", "Cheezy",566123, 1667, BookState.Borrowed));

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
			if(	grid.getSelectedItems().size() >2) {
				System.out.println("NAGYOBB");
			}
		});
				
		grid.setSizeFull();
		layout.addComponent(grid);
		
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