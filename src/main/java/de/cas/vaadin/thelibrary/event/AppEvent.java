package de.cas.vaadin.thelibrary.event;

import de.cas.vaadin.thelibrary.ui.view.CreateContent;

/**
 * @author mate.biro
 * Class for actions that uses EventBus actions
 *
 */
public abstract class AppEvent {
	/*Handles a login request and sets the username-password pair*/
	public static final class LoginRequestEvent{
		private String username, password;
		public LoginRequestEvent(String username, String password) {
			this.username = username;
			this.password = password;
		}
		public String getUsername() {
			return username;
		}
		
		public String getPassword() {
			return password;
		}
		//Setters are pretty much useless
		public void setPassword(String password) {
			this.password = password;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		
	}
	/*Handles events to change the view of the app. It needs a 
	 * CreateContent object as contructor parameter*/
	public static final class ChangeViewEvent{
		private CreateContent container ;
		public ChangeViewEvent(CreateContent container) {
			this.container = container;
		}
		public CreateContent getContainer() {
			return container;
		}
		public void setContainer(CreateContent container) {
			this.container = container;
		}
		
	}
	/*Handles logout requests. There is nothing needed to be done*/
	public static final class LogoutRequestEvent{
		
	}
	/*Handles the event when notification is sent to the admin.
	 * Right now, it only can happen when deleting rents*/
	public static final class NotificationEvent{
		private String notificationMessage;
		public NotificationEvent(String notificationMessage) {
			this.notificationMessage = notificationMessage;
		}
		public String getNotificationMessage() {
			return notificationMessage;
		}
		public void setNotificationMessage(String notificationMessage) {
			this.notificationMessage = notificationMessage;
		}
		
	}
	
	

}