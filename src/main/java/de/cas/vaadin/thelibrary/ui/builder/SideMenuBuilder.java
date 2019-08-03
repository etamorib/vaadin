package de.cas.vaadin.thelibrary.ui.builder;


import java.util.ArrayList;
import java.util.LinkedHashMap;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
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
	
	
	
	public SideMenuBuilder() {
		//addStyleName("valo-menu");
		setPrimaryStyleName(ValoTheme.MENU_ROOT);
		setSizeFull();
		
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
        
        //Logout
        MenuBar logoutmenu = new MenuBar();
        logoutmenu.addItem("Logout", VaadinIcons.SIGN_OUT, new Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				AppEventBus.post(new LogoutRequestEvent());
				
			}
        	
        });
        logoutmenu.addStyleName("user-menu");
        menuContent.addComponent(logoutmenu);
        
        //Responsive
        final Button showMenu = new Button("Menu");
        showMenu.addClickListener(e->{
        	if (menuContent.getStyleName().contains("valo-menu-visible")) {
            	menuContent.removeStyleName("valo-menu-visible");
            } else {
            	menuContent.addStyleName("valo-menu-visible");
            }
        });
        showMenu.addStyleName(ValoTheme.BUTTON_PRIMARY);
        showMenu.addStyleName(ValoTheme.BUTTON_SMALL);
        showMenu.addStyleName("valo-menu-toggle");
        showMenu.setIcon(VaadinIcons.MENU);
        menuContent.addComponent(showMenu);
        
        
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