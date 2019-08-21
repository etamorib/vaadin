package de.cas.vaadin.thelibrary.ui.builder;


import java.util.Set;
import com.google.inject.Inject;
import org.vaadin.teemusa.sidemenu.SideMenu;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.CustomComponent;
import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


/**
 * @author mate.biro
 * This class builds the sidemenu.
 *
 */
@SuppressWarnings("serial")
public class SideMenuBuilder extends CustomComponent {
	
	private final String title = "CAS Library";
	private final  NotificationWindowBuilder notificationWindowBuilder;
	private final Set<CreateContent> contentItems;
	private SideMenu menu ;

	@Inject
	public SideMenuBuilder(SideMenu menu ,Set<CreateContent> contentItems, NotificationWindowBuilder n) {
		this.contentItems = contentItems;
		this.menu = menu;
		notificationWindowBuilder = n;
		addItemsToMenu(contentItems);
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
		//Notifications will not be stored after the app terminates
		//For that, i would need to store in database (maybe todo?)
		menu.addUserMenuItem("Notifications", notificationWindowBuilder::openNotifications);
	}

	/*Sets the menu items, and sets the action of the items.
	 * If a menu item is clicked it posts a ChangeViewEvent,
	 * which will change the "view"
	 */
	private void addItemsToMenu(Set<CreateContent> contentItems) {
		for(CreateContent c : contentItems) {
			menu.addMenuItem(c.getName(), c.menuIcon(),()->{
				AppEventBus.post(new ChangeViewEvent(c));
			});
		}
	}

	public SideMenu getSideMenu() {
		return this.menu;
	}

	
}