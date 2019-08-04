package de.cas.vaadin.thelibrary.ui.builder;


import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.vaadin.teemusa.sidemenu.SideMenu;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ThemeResource;
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
	
	private ArrayList<CreateContent> menuItems ;
	private final String title = "CAS Library";
	

	private SideMenu menu = new SideMenu();
	
	public SideMenuBuilder() {
		menuItems =  new ArrayList<>();
		menuItems.clear();
		fillArray(new BooksView(), new Readers(), new NewRental(), new Rentals(), new WaitList());
		System.out.println(menuItems.size());
		addItemsToMenu(menuItems);
		AppEventBus.register(this);
		styleMenu();
		setAdmin();

		
	}
	
	private void styleMenu() {
		menu.setMenuCaption(title);
	}
	
	//TODO: if there was more admin
	//i could have a name + personal icon
	private void setAdmin() {
		menu.setUserName("Admin");
		menu.setUserIcon(VaadinIcons.USER_CHECK);
		menu.clearUserMenu();
		menu.addUserMenuItem("Sign out", VaadinIcons.SIGN_OUT,()->{
			AppEventBus.post(new LogoutRequestEvent());
		});
	}
	
	private void addItemsToMenu(ArrayList<CreateContent> contents) {
		for(CreateContent c : contents) {
			menu.addMenuItem(c.getName(), c.menuIcon(),()->{
				AppEventBus.post(new ChangeViewEvent(c));
			});		
			
		}
		
	}
	
	private void fillArray(CreateContent ...contents) {
		for(CreateContent c : contents) {
			menuItems.add(c);
		}
	}
	
	public void addNewItem(CreateContent c) {
		menuItems.add(c);
	}
	
	public void addNewItemWithPosition(CreateContent c, int index) {
		menuItems.add(index, c);
	}
	
	
	public ArrayList<CreateContent> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<CreateContent> menuItems) {
		this.menuItems = menuItems;
	}

	public SideMenu getSideMenu() {
		return this.menu;
	}
	

	
}