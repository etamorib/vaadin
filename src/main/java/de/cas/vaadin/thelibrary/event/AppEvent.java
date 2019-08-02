package de.cas.vaadin.thelibrary.event;

import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public abstract class AppEvent {
	
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
	
	public static final class ChangeViewEvent{
		private CreateContent container;
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
	
	public static final class LogoutRequestEvent{
		
	}
	
	

}