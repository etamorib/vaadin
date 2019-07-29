package de.cas.vaadin.thelibrary;

import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
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

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of an HTML page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	@Override
    protected void init(VaadinRequest vaadinRequest) {
		/*Label title = new Label("Menu");
		title.addStyleName(ValoTheme.MENU_TITLE);
		
		Button view1 = new Button("View 1", e->getNavigator().navigateTo("Books"));
		view1.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
		
		CssLayout menu = new CssLayout(title, view1);
		menu.addStyleName(ValoTheme.MENU_ROOT);
		
		CssLayout viewContainer = new CssLayout();
		
		HorizontalLayout mainLayout = new HorizontalLayout(menu, viewContainer);
		mainLayout.setSizeFull();
		setContent(mainLayout);
		
		Navigator navigator = new Navigator(this, viewContainer);
		navigator.addView("Books", BooksView.class);*/
		//TODO: NAVIGATION
		//TODO: BETTER DESIGN
		ArrayList<String> menuItems = new ArrayList<String>() { 
            { 
                add("BooksView"); 
                add("ReadersView"); 
                add("RentalView"); 
            } 
        }; 
		SideMenuBuilder sideMenu = new SideMenuBuilder("Menu",menuItems);
		sideMenu.setMenuButtonStyle(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
		sideMenu.setTitleStyle(ValoTheme.MENU_TITLE);
		setContent(sideMenu.build());
		Navigator navigator = new Navigator(this, sideMenu.getViewContainer());
		navigator.addView("Books", BooksView.class);
    }
	
	

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
