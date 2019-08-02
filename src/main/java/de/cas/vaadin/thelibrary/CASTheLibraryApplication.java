package de.cas.vaadin.thelibrary;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Admin;
import de.cas.vaadin.thelibrary.event.AppEvent.LoginRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;
import de.cas.vaadin.thelibrary.ui.view.LoginView;
import de.cas.vaadin.thelibrary.ui.view.MainView;

/**
 * @author mate.biro
 * CAS Trial work - the library
 * This is the entry point of the application
 *
 */
@SuppressWarnings("serial")
public class CASTheLibraryApplication extends UI {
	
	private AppEventBus eventBus = new AppEventBus();
	
	@Override
	protected void init(VaadinRequest request) {
		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		AppEventBus.register(this);
		
		updateContent();
		
		
	}
	
	
	/**
	 * This method checks sets the content to the LoginView or 
	 * the MainView depending on the session.
	 * If there an Admin attribute set to the session, updateContent
	 * navigates to the MainView, else it navigates to LoginView
	 */
	private void updateContent() {
		Admin admin = (Admin)VaadinSession.getCurrent().getAttribute(Admin.class.getName());
		if(admin!=null) {
			setContent(new MainView());
			
		}else {
			setContent(new LoginView());
		}
			
	}
	  /**
	 * @param LoginRequestEvent e
	 * loginRequest fires then a LoginRequestEvent e occurs
	 * The method authenticates the requested login.
	 * If sucessful, it sets the session attribute and updates the content
	 */
	@Subscribe
	  public void loginRequest(final LoginRequestEvent e) {
		  AuthenticationHandler aut = new AuthenticationHandler(e.getUsername(), e.getPassword());
		  if(aut.authenticate()!=null) {
			  VaadinSession.getCurrent().setAttribute(Admin.class.getName(), aut.authenticate());
			  updateContent();

		  }
	  }
	
	@Subscribe
	public void logoutRequest(final LogoutRequestEvent e) {
		VaadinSession.getCurrent().close();
		Page.getCurrent().reload();
	}
	  
	
	  /**
	 * @return the AppEventBus of the application
	 */
	public static AppEventBus getEventBus() {
		  return ((CASTheLibraryApplication)getCurrent()).eventBus;
	  }
	  
	  
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = CASTheLibraryApplication.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
	
}