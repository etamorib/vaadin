package de.cas.vaadin.thelibrary.ui.builder;


import java.util.ArrayList;

import org.vaadin.teemusa.sidemenu.SideMenu;

import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.NotificationEvent;
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
	private Window notificationWindow;
	private HorizontalLayout notificationLayout = new HorizontalLayout();
	

	private SideMenu menu = new SideMenu();
	
	public SideMenuBuilder() {
		menuItems =  new ArrayList<>();
		menuItems.clear();
		fillArray(new BooksView(), new Readers(), new NewRental(), new Rentals(), new WaitList());
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
		menu.addUserMenuItem("Notifications", ()->{
			openNotifications();
		});
	}
	
	@Subscribe
	public void addNotification(final NotificationEvent e) {
		
		Label msg = new Label(e.getNotificationMessage());
		Button remove = new Button();
		remove.setIcon(VaadinIcons.CLOSE);
		remove.addClickListener(event->{
			notificationLayout.removeComponent(msg);
			notificationLayout.removeComponent(remove);
		});
		notificationLayout.addComponents(msg, remove);
	}
	
	private void openNotifications() {
		notificationWindow = new Window();
		notificationWindow.setModal(true);
		if(notificationLayout.getComponentCount()==0) {
			Notification.show("You have no notifications");
		}else {
			notificationWindow.setContent(notificationLayout);
			UI.getCurrent().addWindow(notificationWindow);
		}
		
		

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