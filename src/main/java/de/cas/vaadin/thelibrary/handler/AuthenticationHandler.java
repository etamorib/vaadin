package de.cas.vaadin.thelibrary.handler;

import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

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
		Notification error = new Notification("", Type.ERROR_MESSAGE);
		error.setPosition(Position.TOP_CENTER);
		error.setDelayMsec(3000);
		if(username.equals("admin") && password.equals("admin")) {
			return null;
		}else if(username.equals("") || password.equals("")) {
			error.setCaption(MISSING);
			return error;
		}else {
			error.setCaption("INVALID");
			return error;
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
