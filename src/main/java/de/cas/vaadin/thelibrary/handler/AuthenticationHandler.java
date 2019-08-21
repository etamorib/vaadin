package de.cas.vaadin.thelibrary.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import de.cas.vaadin.thelibrary.model.bean.Admin;

/**
 * @author mate.biro
 * Simple class for authenticating login requests.
 *
 */
public class AuthenticationHandler implements AuthenticationInterface {

	private String username, password;
	private static final String INVALID = "Invalide username or password";

	@Inject
	public AuthenticationHandler(@Assisted("username") String username, @Assisted("password") String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * @return a Notification based on the error type or
	 * null if there was no error.
	 * 
	 */
	@Override
	public Notification check() {
		Notification error = new Notification("", Type.ERROR_MESSAGE);
		error.setPosition(Position.TOP_CENTER);
		error.setDelayMsec(1500);
		if(username.equals("admin") && password.equals("admin")) {
			return null;
		}else {
			error.setCaption(INVALID);
			return error;
		}
	}
	
	/**
	 * @return an Admin object is username and password
	 * are correct or null if they're not.
	 */
	@Override
	public Admin authenticate() {
		if (username.equals("admin") && password.equals("admin")) {
			return new Admin(username, password);
		}else {
			return null;
		}
	}


}
