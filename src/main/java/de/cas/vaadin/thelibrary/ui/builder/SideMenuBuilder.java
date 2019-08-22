package de.cas.vaadin.thelibrary.ui.builder;


import java.util.Set;

import com.google.inject.Inject;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;
import org.vaadin.teemusa.sidemenu.SideMenu;
import com.vaadin.icons.VaadinIcons;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


/**
 * @author mate.biro
 * This class builds the sidemenu.
 *
 */
@SuppressWarnings("serial")
public class SideMenuBuilder extends SideMenu {
	
	private final String title = "CAS Library";
	private final  NotificationWindowBuilder notificationWindowBuilder;
	private final Set<CreateContent> contentItems;
	private Navigator navigator;

	@Inject
	public SideMenuBuilder(Set<CreateContent> contentItems, NotificationWindowBuilder n) {
		this.contentItems = contentItems;

		notificationWindowBuilder = n;
		navigator = new Navigator(UI.getCurrent(), this);
		addItemsToMenu(contentItems);
		styleMenu();
		setAdmin();
		//System.out.println("SIDEMENUBUILDER");

	}
	public Navigator getNavigator(){
		return this.navigator;
	}
	
	//This method is for adding styles to the sidemenu
	private void styleMenu() {
		setMenuCaption(title);
	}
	

	private void setAdmin() {
		setUserName("Admin");
		setUserIcon(VaadinIcons.USER_CHECK);
		clearUserMenu();
		addUserMenuItem("Sign out", VaadinIcons.SIGN_OUT,()->{
			AppEventBus.post(new LogoutRequestEvent());
		});
		//Notifications will not be stored after the app terminates
		//For that, i would need to store in database (maybe todo?)
		addUserMenuItem("Notifications", notificationWindowBuilder::openNotifications);
	}

	/*Sets the menu items, and sets the action of the items.
	 * If a menu item is clicked it posts a ChangeViewEvent,
	 * which will change the "view"
	 */
	private void addItemsToMenu(Set<CreateContent> contentItems) {

		for(CreateContent c: contentItems){
			navigator.addView(c.getName(), c);
			addNavigation(c.getName(), c.menuIcon(), c.getName());
		}

	}

}