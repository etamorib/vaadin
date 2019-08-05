package de.cas.vaadin.thelibrary.ui.view.content;

import org.vaadin.alump.fancylayouts.FancyCssLayout;

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

@SuppressWarnings("serial")
public class Readers extends HorizontalLayout implements CreateContent {
	
	private final String name = "Readers";
	private HorizontalLayout mainLayout;
	private VerticalLayout left;
	private FancyCssLayout editLayout = new FancyCssLayout();
	private Grid<Reader> grid =  new Grid<>(Reader.class);
	private ReaderController controller = new ReaderController();
	private Button add, del, edit;
	private ListDataProvider<Reader> dataProvider ;
	
	
	@Override
	public Component buildContent() {
		mainLayout = new HorizontalLayout();
		mainLayout.setSizeFull();
		left = new VerticalLayout();
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
		
		//Drag grid
		GridDragSource<Reader> source = new GridDragSource<Reader>(grid);
		source.setEffectAllowed(EffectAllowed.MOVE);
		
		
		//Drop grid
		DropTargetExtension<Button> dropDeleteTarget = new DropTargetExtension<>(del);
		DropTargetExtension<Button> dropEditTarget = new DropTargetExtension<Button>(edit);
		dropDeleteTarget.setDropEffect(DropEffect.MOVE);
		dropEditTarget.setDropEffect(DropEffect.MOVE);
		dropDeleteTarget.addDropListener(e->{
			if(controller.delete(grid.getSelectedItems())) {
				new Notification("",
					    "Reader(s) have been deleted!",
					    Notification.Type.WARNING_MESSAGE, true)
					    .show(Page.getCurrent());
				grid.setItems(controller.getItems());
				grid.deselectAll();
			}
		});
		
		dropEditTarget.addDropListener(e->{
			if(grid.getSelectedItems().size()>0) {
				grid.setEnabled(false);
				for(Reader b: grid.getSelectedItems()) {
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
	
	private void buildEditLayout(final FancyCssLayout editLayout, Reader b) {
		
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
					grid.setEnabled(true);
					editLayout.removeAllComponents();
					mainLayout.removeComponent(editLayout);
					grid.deselectAll();
					grid.setSizeFull();
				}
				
			});
			
			
			
			//Form
			TextField id = new TextField("id");
			id.setEnabled(false);
			id.setValue(String.valueOf(b.getId()));
			TextField name = new TextField("Name");
			name.setValue(b.getName());
			TextField address = new TextField("Address");
			address.setValue(b.getAddress());
			
			//TODO: NumberField
			TextField email = new TextField("Email");
			email.setValue(b.getEmail());
			TextField phone = new TextField("Phone number");
			phone.setValue(String.valueOf(b.getPhoneNumber()));

			
			//Save button
			save.addClickListener(e->{
				Reader reader = new Reader(name.getValue(), address.getValue(), email.getValue(), 
											Integer.parseInt(id.getValue()), Long.parseLong(phone.getValue()));
				if(editLayout.getComponentCount()>1) {
					if(controller.update(reader)) {
						Notification.show("Update successful");
						dataProvider = new ListDataProvider<>(controller.getItems());
						grid.setDataProvider(dataProvider);
						editLayout.fancyRemoveComponent(editForm);
						
					}else {
						Notification.show("Something went wrong!");
						editLayout.fancyRemoveComponent(editForm);
					}
				}else {
					if(controller.update(reader)) {
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
			editForm.addComponents(name, address, email, phone, id, save, cancel);
			editLayout.addComponent(editForm);
		
	}

	private Component buildButtons() {
		HorizontalLayout buttons = new HorizontalLayout();
		//Add Button
		add = new Button();
		add.setIcon(VaadinIcons.PLUS);
		add.setDescription("Add new reader to database");
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
				for(Reader r: grid.getSelectedItems()) {
					buildEditLayout(editLayout,  r);
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
			dataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
									reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));		
			
		});
		buttons.addComponents(add, del, edit, search);
		return buttons;
	}

	
	
	private void addingWindow() {
		Window window = new Window();
		window.setCaption("Add new reader");
		//VerticalLayout layout = new VerticalLayout();
		//layout.addComponents(new Label("Title:"),new TextField(), new Button("Add"));
		FormLayout form = new FormLayout();
		
		//author
		TextField name = new TextField("Name");
		name.setIcon(VaadinIcons.PENCIL);
		name.setRequiredIndicatorVisible(true);
		form.addComponent(name);
		
		//title
		TextField address = new TextField("Address");
		address.setIcon(VaadinIcons.HOME);
		address.setRequiredIndicatorVisible(true);
		
		//ID
		//TODO: NumberField jobb lenne
		TextField email = new TextField("Email");
		email.setIcon(VaadinIcons.AT);
		email.setRequiredIndicatorVisible(true);
		//Year
		//TODO: Select kéne
		TextField phone = new TextField("Phone number");
		phone.setIcon(VaadinIcons.PHONE);
		phone.setRequiredIndicatorVisible(true);
		
		Button add = new Button("Add");
		add.setStyleName(ValoTheme.BUTTON_FRIENDLY);
		add.setClickShortcut(KeyCode.ENTER);
		add.setIcon(VaadinIcons.PLUS);
		add.addClickListener(e->{
			Reader reader = new Reader(name.getValue(), address.getValue(), email.getValue(), Long.parseLong(phone.getValue()));
			if(controller.add(reader)) {
				window.close();
				Notification.show("Book has been added to database");
				grid.setItems(controller.getItems());
				
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
