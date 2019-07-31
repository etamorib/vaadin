package de.cas.vaadin.thelibrary;

import javax.servlet.annotation.WebServlet;

import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.bean.Admin;
import de.cas.vaadin.thelibrary.event.AppEvent.LoginRequestEvent;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.LoginView;
import de.cas.vaadin.thelibrary.ui.view.MainView;

public class CASTheLibraryApplication extends UI {
	
private AppEventBus eventBus = new AppEventBus();
	
	@Override
	protected void init(VaadinRequest request) {
		addStyleName(ValoTheme.UI_WITH_MENU);
		Responsive.makeResponsive(this);
		AppEventBus.register(this);
		
		updateContent();
		
		
	}
	  private void updateContent() {
		Admin admin = (Admin)VaadinSession.getCurrent().getAttribute(Admin.class.getName());
		System.out.println(VaadinSession.getCurrent().getAttribute(Admin.class.getName()));
		if(admin!=null) {
			setContent(new MainView());
			//getNavigator().navigateTo(getNavigator().getState());
			System.out.println("UPDATE!");
			
		}else {
			setContent(new LoginView());
		}
		
		
	}
	  @Subscribe
	  public void loginRequest(final LoginRequestEvent e) {
		  AuthenticationHandler aut = new AuthenticationHandler(e.getUsername(), e.getPassword());
		  if(aut.authenticate()!=null) {
			  VaadinSession.getCurrent().setAttribute(Admin.class.getName(), aut.authenticate());
			  System.out.println("Authenticated!");
			  updateContent();

		  }
	  }
	  
	  //Solved singleton problem
	  public static AppEventBus getEventBus() {
		  return ((CASTheLibraryApplication)getCurrent()).eventBus;
	  }
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	    @VaadinServletConfiguration(ui = CASTheLibraryApplication.class, productionMode = false)
	    public static class MyUIServlet extends VaadinServlet {
	    }
	
}
