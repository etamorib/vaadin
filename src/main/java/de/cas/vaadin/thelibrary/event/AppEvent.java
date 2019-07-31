package de.cas.vaadin.thelibrary.event;

import com.vaadin.ui.ComponentContainer;

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
		private Class<? extends ComponentContainer> container;
		public ChangeViewEvent(Class<? extends ComponentContainer> container) {
			this.container = container;
		}
		public Class<? extends ComponentContainer> getContainer() {
			return container;
		}
		public void setContainer(Class<? extends ComponentContainer> container) {
			this.container = container;
		}
		
	}
	
	

}
