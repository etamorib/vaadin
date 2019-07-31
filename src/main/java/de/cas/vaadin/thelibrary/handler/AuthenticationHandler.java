package de.cas.vaadin.thelibrary.handler;

import com.vaadin.ui.Notification;

import de.cas.vaadin.thelibrary.bean.Admin;

public class AuthenticationHandler {

	private String username, password;
	private final String INVALID = "Invalide username or password";
	private final String MISSING = "Missing username or password";
	public AuthenticationHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Notification check() {
		if(username.equals("admin") && password.equals("admin")) {
			return null;
		}else if(username.equals("") || password.equals("")) {
			return new Notification(MISSING,Notification.TYPE_ERROR_MESSAGE);
		}else {
			return new Notification(INVALID, Notification.TYPE_ERROR_MESSAGE);
		}
	}
	
	public Admin authenticate() {
		if (username.equals("admin") && password.equals("admin")) {
			return new Admin(username, password);
		}else {
			return null;
		}
	}
	
}
