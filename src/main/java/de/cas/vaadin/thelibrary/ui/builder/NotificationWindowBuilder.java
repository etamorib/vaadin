package de.cas.vaadin.thelibrary.ui.builder;

import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.cas.vaadin.thelibrary.event.AppEvent.NotificationEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;

public class NotificationWindowBuilder {
	
	private static HorizontalLayout notificationLayout = new HorizontalLayout();
	private static Window window = new Window();
	//private static NotificationWindowBuilder instance = new NotificationWindowBuilder();
	NotificationWindowBuilder() {
		AppEventBus.register(this);
		
	}
	/*This method waits for NotificationEvent
	*If a notification event occurs, it sets the
	*message of the event to a label, and adds to a layout
	 */
	@Subscribe
	public void addNotification(final NotificationEvent e) {
		Notification.show("You have new notification", Type.TRAY_NOTIFICATION);
		Label msg = new Label(e.getNotificationMessage());
		Button remove = new Button();
		remove.setIcon(VaadinIcons.CLOSE);
		remove.addClickListener(event->{
			notificationLayout.removeComponent(msg);
			notificationLayout.removeComponent(remove);
		});
		notificationLayout.addComponents(msg, remove);
	}
	
	/*This makes a pop-up window. The window contains nothing, if there
	 * is no notification.
	 * If there was a notification, it contains the message of that.
	 * */
	void openNotifications() {
		
		window.addStyleName("add-window");
		window.setModal(true);
		if(notificationLayout.getComponentCount()==0) {
			Notification.show("You have no notifications");
		}else {
			window.setContent(notificationLayout);
			UI.getCurrent().addWindow(window);
		}
		
	}

}
