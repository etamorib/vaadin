package de.cas.vaadin.thelibrary.ui.view.content;

import org.vaadin.alump.fancylayouts.FancyCssLayout;
import org.vaadin.ui.NumberField;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.components.grid.GridDragSource;
import com.vaadin.ui.dnd.DropTargetExtension;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.ReaderController;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


/**
 * This class is similar to BooksView
 * Adding , deleting, updating users is possible
 * @author mate.biro
 *
 */
public class Readers implements CreateContent {
	
	private final String name = "Readers";
	private HorizontalLayout mainLayout;
	private FancyCssLayout editLayout = new FancyCssLayout();
	private Grid<Reader> grid =  new Grid<>(Reader.class);
	private ReaderController controller = new ReaderController();
	private Button add, del, edit;
	private ListDataProvider<Reader> dataProvider ;
	private FormLayout editForm;


	@Override
	public Component buildContent() {
		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		VerticalLayout left = new VerticalLayout();
		Label title = new Label("Registered readers");
		title.setStyleName(ValoTheme.LABEL_H1);
		left.addComponents(title,buildButtons(), buildGrid());
		mainLayout.addComponent(left);
		return mainLayout;
	}
	
	private Component buildGrid() {
		dataProvider = new ListDataProvider<>(controller.getItems());
		//To make sure column order
		//grid.setColumns("id","name", "address", "email", "phonenumber");
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		grid.setSizeFull();
		return grid;
	}
	
	//Building the layout for right side editing
	private void buildEditLayout(Reader b) {
		
			editForm = new FormLayout();
			
			//Buttons
			Button save = new Button("Save");
			save.setStyleName(ValoTheme.BUTTON_PRIMARY);
			Button cancel = new Button("Cancel");
			cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);
			setCancelClickListener(cancel);
			
			//Form
			TextField id = new TextField("id");
			id.setEnabled(false);
			id.setValue(String.valueOf(b.getId()));
			TextField name = new TextField("Name");
			name.setValue(b.getName());
			TextField address = new TextField("Address");
			address.setValue(b.getAddress());
			
			
			TextField email = new TextField("Email");
			email.setValue(b.getEmail());
			NumberField phone = new NumberField("Phone number");
			phone.setValue(String.valueOf(b.getPhoneNumber()));

			
			//Save button
			save.addClickListener(e->{
				//New reader object based on the values of the fields
				Reader reader = new Reader(name.getValue(), address.getValue(), email.getValue(), 
											Integer.parseInt(id.getValue()), Long.parseLong(phone.getValue()));
				setSaveClickListener(save, reader);


			});
			
			editForm.setSizeFull();
			editForm.addComponents(name, address, email, phone, id, save, cancel);
			editLayout.addComponent(editForm);
		
	}

	private void setSaveClickListener(Button save, Reader reader) {
		if(editLayout.getComponentCount()>1) {
			if(controller.update(reader)) {
				Notification.show("Update successful");
				//Reset the grid
				dataProvider = new ListDataProvider<>(controller.getItems());
				grid.setDataProvider(dataProvider);
				editLayout.fancyRemoveComponent(editForm);

			}
			//If update was unsuccessful
			else {
				Notification.show("Something went wrong!");
				editLayout.fancyRemoveComponent(editForm);
			}
		}
		else {
			if(controller.update(reader)) {
				Notification.show("Update successful");
			}
			else {
				Notification.show("Something went wrong");
			}
			//Reset everything to normal
			grid.setEnabled(true);
			editLayout.removeAllComponents();
			mainLayout.removeComponent(editLayout);
			grid.setSizeFull();
			grid.deselectAll();
			dataProvider = new ListDataProvider<>(controller.getItems());
			grid.setDataProvider(dataProvider);
		}
	}

	private void setCancelClickListener(Button cancel) {
		cancel.addClickListener(e->{
			if(editLayout.getComponentCount()>1) {
				editLayout.fancyRemoveComponent(editForm);

			}
			//If there are no more components, remove everything and reset the view
			else {
				grid.setEnabled(true);
				editLayout.removeAllComponents();
				mainLayout.removeComponent(editLayout);
				grid.deselectAll();
				grid.setSizeFull();
			}

		});

	}

	//Build the delete, edit and add button with the search field
	private Component buildButtons() {
		HorizontalLayout buttons = new HorizontalLayout();
		//Add Button
		add = new Button();
		add.setIcon(VaadinIcons.PLUS);
		add.setStyleName("header-button");
		add.setDescription("Add new reader to database");
		setAddClickListener();
		
		//Del button
		del = new Button();
		del.setIcon(VaadinIcons.TRASH);
		del.setStyleName("header-button");
		del.setDescription("Delete selected items");
		setDelClickListener();
		
		//Edit button
		edit = new Button();
		edit.setIcon(VaadinIcons.PENCIL);
		edit.setStyleName("header-button");
		edit.setDescription("Edit selected items");
		setEditClickListener();
		
		TextField search  = new TextField();
		search.setIcon(VaadinIcons.SEARCH);
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setPlaceholder("Search...");
		search.setValueChangeMode(ValueChangeMode.EAGER);
		setSearchFilter(search);
		buttons.addComponents(add, del, edit, search);
		return buttons;
	}

	private void setSearchFilter(TextField search) {
		search.addValueChangeListener(e->{
			//Filter for both title and author
			dataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
					reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
					reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
					reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));

		});
	}

	private void setEditClickListener() {
		edit.addClickListener(e->{
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				for(Reader r: grid.getSelectedItems()) {
					buildEditLayout(r);
				}
				mainLayout.addComponent(editLayout);
			}else {
				Notification.show("There is nothing to be edited!");
			}

		});
	}

	private void setDelClickListener() {
		del.addClickListener(e->{
			controller.delete(grid.getSelectedItems());
			dataProvider = new ListDataProvider<>(controller.getItems());
			grid.setDataProvider(dataProvider);

		});
	}

	private void setAddClickListener() {
		add.addClickListener(e->{
			addingWindow();
		});
	}


	//The popup window for adding new Reader
	private void addingWindow() {
		Window window = new Window();
		window.setCaption("Add new reader");

		FormLayout form = new FormLayout();
		
		//name
		TextField name = new TextField("Name");
		name.setIcon(VaadinIcons.PENCIL);
		name.setRequiredIndicatorVisible(true);
		form.addComponent(name);
		
		//address
		TextField address = new TextField("Address");
		address.setIcon(VaadinIcons.HOME);
		address.setRequiredIndicatorVisible(true);
		
		//email
		TextField email = new TextField("Email");
		email.setIcon(VaadinIcons.AT);
		email.setRequiredIndicatorVisible(true);
		
		//phone
		NumberField phone = new NumberField("Phone number");
		phone.setNegativeAllowed(false);
		phone.setGroupingUsed(false);
		phone.setIcon(VaadinIcons.PHONE);
		phone.setRequiredIndicatorVisible(true);
		
		Button add = new Button("Add");
		add.setStyleName(ValoTheme.BUTTON_PRIMARY);
		add.setClickShortcut(KeyCode.ENTER);
		add.setIcon(VaadinIcons.PLUS);
		add.addClickListener(e->{
			//Adding new reader to database
			Reader reader = new Reader(name.getValue(), address.getValue(), email.getValue(), Long.parseLong(phone.getValue()));
			if(controller.add(reader)) {
				window.close();
				Notification.show("Book has been added to database");
				//Refresh grid
				dataProvider = new ListDataProvider<>(controller.getItems());
				grid.setDataProvider(dataProvider);
				
			}
		});
		
		form.addComponents(name, address, email, phone, add);
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
		return VaadinIcons.USERS;
	}
	

}
