package de.cas.vaadin.thelibrary.ui.builder;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
import de.cas.vaadin.thelibrary.ui.view.content.BooksView;
import de.cas.vaadin.thelibrary.ui.view.content.NewRental;
import de.cas.vaadin.thelibrary.ui.view.content.Readers;
import de.cas.vaadin.thelibrary.ui.view.content.Rentals;
import de.cas.vaadin.thelibrary.ui.view.content.WaitList;;


@SuppressWarnings("serial")
public class SideMenuBuilder extends CustomComponent {
	private LinkedHashMap<CreateContent, Button> menuItems = new LinkedHashMap<>();
	
	private ArrayList<CreateContent> items = new ArrayList<>();
	
	private static SideMenuBuilder sideMenu = new SideMenuBuilder();
	
	private SideMenuBuilder() {
		//addStyleName("valo-menu");
		setSizeUndefined();
		
		//Adding menu items
		
		
		addMenuItems(addItemsToList(
				new BooksView(), 
				new Readers(), 
				new Rentals(), 
				new NewRental(),
				new WaitList()));
		
		
		setCompositionRoot(buildMenu());
		AppEventBus.register(this);
		
	}
	
	public Component buildMenu() {
		final CssLayout menuContent = new CssLayout();
		//menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        //menuContent.addStyleName("no-vertical-drag-hints");
        //menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setHeight("100%");
        //menuContent.setWidth("20%");
        
        menuContent.addComponents(buildTitle(), buildMenuItems());
        
        return menuContent;

	}
	
	private Component buildMenuItems() {
		CssLayout menuItemsLayout = new CssLayout();
		VerticalLayout buttonWrapper = new VerticalLayout();
		menuItemsLayout.setStyleName("valo-menuitems");
		menuItems.forEach((k,v)->{
			v.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			v.addClickListener(e->{
				AppEventBus.post(new ChangeViewEvent(k));
			});
			
			buttonWrapper.addComponent(v);
			buttonWrapper.setComponentAlignment(v, Alignment.MIDDLE_CENTER);
			
			
		});
		menuItemsLayout.addComponent(buttonWrapper);
		return menuItemsLayout;
	}

	private Component buildTitle() {
		Label title = new Label("LibraryApp");
		title.setSizeUndefined();
		HorizontalLayout titleWrapper = new HorizontalLayout(title);
		titleWrapper.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
		titleWrapper.addStyleName("valo-menu-title");
		titleWrapper.setSpacing(false);
		return titleWrapper;
	}

	//Instance for singleton
	public static SideMenuBuilder Instance() {
		return sideMenu;
	}
	
	
	private ArrayList<CreateContent> addItemsToList(CreateContent...contens) {
		for(CreateContent c : contens) {
			this.items.add(c);
		}
		return this.items;
	}
	
	private void addMenuItems(ArrayList<CreateContent> list) {
		
		for(CreateContent c : list) {
			menuItems.put(c, new Button(c.getName()));
		}
	}

	public LinkedHashMap<CreateContent, Button> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(LinkedHashMap<CreateContent, Button> menuItems) {
		this.menuItems = menuItems;
	}
	

	
}
