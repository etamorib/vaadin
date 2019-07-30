package de.cas.vaadin.thelibrary;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import org.atmosphere.handler.AbstractReflectorAtmosphereHandler.Default;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.LoginForm.LoginListener;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.BooksView;
import de.cas.vaadin.thelibrary.ui.view.DefaultView;
import de.cas.vaadin.thelibrary.ui.view.wrapper.AbstractViewWrapper;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	
	private SideMenuBuilder sideMenu = new SideMenuBuilder();
	private HorizontalLayout content;
	private CssLayout viewContainer;
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		setNavigation(new DefaultView(), new BooksView());
		viewContainer = new CssLayout();
		HorizontalLayout mainLayout = new HorizontalLayout(createSideMenu(), viewContainer);
		mainLayout.setSizeFull();
		setContent(mainLayout);
		
		
    }
	
	
	private CssLayout createSideMenu() {
		
		Label title = new Label("Library");
		title.addStyleName(ValoTheme.MENU_TITLE);
		sideMenu.getMenuItems().forEach((k,v)->k.addClickListener(e->{
			getNavigator().navigateTo(v.name());
			
		}));
		CssLayout menu = new CssLayout();
		menu.addComponents(title);
		sideMenu.getMenuItems().forEach((k,v)->menu.addComponent(k));
		menu.addStyleName(ValoTheme.MENU_ROOT);
		return menu;
		
	}
	
	private void setNavigation(AbstractViewWrapper... menuItems) {
		sideMenu.createMenuButtons(menuItems);
		Navigator navigator = new Navigator(this, viewContainer);
		sideMenu.getMenuItems().forEach((k,v)->navigator.addView(v.name(), v));
		
		
	}
	

	
	
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
