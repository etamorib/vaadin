package de.cas.vaadin.thelibrary.event;

import com.google.inject.*;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;
import de.cas.vaadin.thelibrary.handler.AuthenticationInterface;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.modules.AppModule;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

import java.util.Set;

/**
 * @author mate.biro
 * Class for actions that uses EventBus actions
 *
 */
public abstract class AppEvent {
	/*Handles a login request and sets the username-password pair*/
	public static final class LoginRequestEvent{
		private String username, password;
		private AuthenticationInterface authenticationHandler;

		@Inject
		public LoginRequestEvent(String username, String password, AuthenticationInterface authenticationHandler) {
			this.authenticationHandler = authenticationHandler;
			this.username = username;
			this.password = password;
		}
		public String getUsername() {
			return username;
		}

		public AuthenticationInterface getAuthenticationHandler() {
			return authenticationHandler;
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

		@Inject
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
	/*Handles logout requests. There is nothing needed to be done
	 * We just need to know when it is posted*/
	public static final class LogoutRequestEvent{
		
	}
	/*Handles the event when notification is sent to the admin.
	 * Right now, it only  happens when deleting rents*/
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

	public static final class CloseOpenedWindowsEvent{

	}

	public static final class EditObjectEvent{

	}
	public static final class SearchFilterEvent{
		private String str;
		public SearchFilterEvent(String str){
			this.str = str;
		}

		public String getValue() {
			return str;
		}

		public void setValue(String str) {
			this.str = str;
		}
	}

	public static final class SelectedBooksEvent{
		private Set<Book> books = null;
		public SelectedBooksEvent(Set<Book> books){
			this.books=books;
		}

		public Set<Book> getBooks() {
			return books;
		}
	}

	public static final class TabChangeEvent{

	}

	public static final class SelectedReaderEvent{
		private Reader reader = null;
		public SelectedReaderEvent(Reader reader){
			this.reader = reader;
		}

		public Reader getReader() {
			return reader;
		}
	}


	
	

}