package de.cas.vaadin.thelibrary.ui.builder;


import java.util.ArrayList;

import de.cas.vaadin.thelibrary.ui.view.content.*;
import org.vaadin.teemusa.sidemenu.SideMenu;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.CustomComponent;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
;

/**
 * @author mate.biro
 * This class builds the sidemenu.
 *
 */
@SuppressWarnings("serial")
public class SideMenuBuilder extends CustomComponent {
	
	private ArrayList<CreateContent> menuItems ;
	private final String title = "CAS Library";
	//So the notification can be carried over view changes
	private final static NotificationWindowBuilder notificationWindowBuilder = new NotificationWindowBuilder();

	private SideMenu menu = new SideMenu();
	
	public SideMenuBuilder() {
		menuItems =  new ArrayList<>();
		menuItems.clear();
		fillArray(new BooksView(), new Readers(), new NewRental(), new Rentals(), new WaitList(), new NewBookView());
		addItemsToMenu(menuItems);
		styleMenu();
		setAdmin();
		}
	
	//This method is for adding styles to the sidemenu
	private void styleMenu() {
		menu.setMenuCaption(title);
	}
	
	//This method sets the UserMenuItem
	//TODO: if there was more admin, it could be set correspondingly 
	private void setAdmin() {
		menu.setUserName("Admin");
		menu.setUserIcon(VaadinIcons.USER_CHECK);
		menu.clearUserMenu();
		menu.addUserMenuItem("Sign out", VaadinIcons.SIGN_OUT,()->{
			AppEventBus.post(new LogoutRequestEvent());
		});
		menu.addUserMenuItem("Notifications", ()->{
			//Notifications will not be stored after the app terminates
			//For that, i would need to store in database (maybe todo?)
			notificationWindowBuilder.openNotifications();
		});
	}

	/*Sets the menu items, and sets the action of the items.
	 * If a menu item is clicked it posts a ChangeViewEvent,
	 * which will change the "view"
	 */
	private void addItemsToMenu(ArrayList<CreateContent> contents) {
		for(CreateContent c : contents) {
			menu.addMenuItem(c.getName(), c.menuIcon(),()->{
				AppEventBus.post(new ChangeViewEvent(c));
			});				
		}
	}
	/*Basically fills the menuItem array with contents
	 * in the parameter
	*/
	private void fillArray(CreateContent ...contents) {
		for(CreateContent c : contents) {
			menuItems.add(c);
		}
	}
	/**
	 * Adds a new menu item to the sidebar
	 * @param A CreateContent object
	 */
	public void addNewItem(CreateContent c) {
		menuItems.add(c);
	}
	/**
	 * Adds a new menu item to the sidebar
	 * @param A CreateContent object
	 * @param index where it will be put
	 */
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