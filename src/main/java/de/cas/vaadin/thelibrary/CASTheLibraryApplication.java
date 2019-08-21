package de.cas.vaadin.thelibrary;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LoginRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEvent.LogoutRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.handler.AuthenticationFactory;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;
import de.cas.vaadin.thelibrary.model.bean.Admin;
import de.cas.vaadin.thelibrary.modules.AppModule;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.loginview.LoginView;
import de.cas.vaadin.thelibrary.ui.view.MainView;
import de.cas.vaadin.thelibrary.utils.SendMail;

/**
 * @author mate.biro
 * CAS Trial work - the library
 * This is the entry point of the application
 *
 */
@Theme("mytheme")
@SuppressWarnings("serial")
public class CASTheLibraryApplication extends UI {


	//Injector
	private static Injector injector = Guice.createInjector(new AppModule());


	private AppEventBus eventBus = new AppEventBus();
	@Inject AuthenticationFactory authenticationFactory;


	@Override
	protected void init(VaadinRequest request) {
		Page.getCurrent().setTitle("CAS The Library Application");
		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		AppEventBus.register(this);
		
		updateContent();
	}

	/**
	 * This method sets the content to the LoginView or 
	 * the MainView depending on the session.
	 * If there was an Admin attribute set to the session, updateContent
	 * navigates to the MainView, else it navigates to LoginView
	 */
	private void updateContent() {
		Admin admin = (Admin)VaadinSession.getCurrent().getAttribute(Admin.class.getName());
		if(admin!=null) {
			setContent(injector.getInstance(MainView.class));
			
		}else {
			setContent(injector.getInstance(LoginView.class));
		}
			
	}
	  /**
	 * 
	 * A loginRequest fires, then a LoginRequestEvent e occurs
	 * The method authenticates the requested login event.
	 * If successful, it sets the session attribute and updates the content
	 * @param LoginRequestEvent e
	 */
	@Subscribe
	  public void loginRequest(final LoginRequestEvent e) {
		  if(e.getAuthenticationHandler().authenticate()!=null) {
			  VaadinSession.getCurrent().setAttribute(Admin.class.getName(), e.getAuthenticationHandler().authenticate());
			  updateContent();

		  }
	  }
	
	@Subscribe
	public void logoutRequest(final LogoutRequestEvent e) {
		VaadinSession.getCurrent().close();
		getUI().getPage().setLocation("/");
		
	}

	@Subscribe
	public void closeOpenedWindows(final AppEvent.CloseOpenedWindowsEvent e){
		for(Window window : getWindows()){
			window.close();
		}
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