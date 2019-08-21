package de.cas.vaadin.thelibrary.model.bean;

import com.google.inject.Inject;

/**
 * @author mate.biro
 * Bean class for Admin object(s)
 */
public class Admin {
	
	private String username, password;

	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public Admin() {
		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
