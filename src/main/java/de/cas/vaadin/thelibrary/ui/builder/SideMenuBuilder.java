package de.cas.vaadin.thelibrary.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.BooksView;
import de.cas.vaadin.thelibrary.ui.view.DefaultView;
import de.cas.vaadin.thelibrary.ui.view.wrapper.AbstractViewWrapper;
import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;;


public class SideMenuBuilder extends CustomComponent {
	private HashMap<Class<? extends ComponentContainer>, Button> menuItems = new HashMap<>();
	
	private static SideMenuBuilder sideMenu = new SideMenuBuilder();
	
	public SideMenuBuilder() {
		addStyleName("valo-menu");
		setSizeUndefined();
		addMenuItem(BooksView.class, new Button("Books"));
		addMenuItem(DefaultView.class, new Button("Test"));
		setCompositionRoot(buildMenu());
		
		AppEventBus.register(this);
		
	}
	
	public Component buildMenu() {
		final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth("250px");
        menuContent.setHeight("100%");
        
        menuContent.addComponents(buildTitle(), buildMenuItems());
        
        return menuContent;

	}
	
	private Component buildMenuItems() {
		CssLayout menuItemsLayout = new CssLayout();
		VerticalLayout buttonWrapper = new VerticalLayout();
		menuItemsLayout.setStyleName("valo-menuitems");
		menuItems.forEach((k,v)->{
			v.addStyleName(ValoTheme.BUTTON_BORDERLESS);
			
			v.addClickListener(e->{
				AppEventBus.post(new ChangeViewEvent(k));
			});
			
			buttonWrapper.addComponent(v);
			buttonWrapper.setComponentAlignment(v, Alignment.MIDDLE_CENTER);
			
			
		});
		menuItemsLayout.addComponent(buttonWrapper);
		return menuItemsLayout;
	}

	private Component buildTitle() {
		Label title = new Label("LibraryApp");
		title.setSizeUndefined();
		HorizontalLayout titleWrapper = new HorizontalLayout(title);
		titleWrapper.setComponentAlignment(title, Alignment.MIDDLE_CENTER);
		titleWrapper.addStyleName("valo-menu-title");
		titleWrapper.setSpacing(false);
		return titleWrapper;
	}

	public static SideMenuBuilder Instance() {
		return sideMenu;
	}
	
	private void addMenuItem(Class<? extends ComponentContainer> view, Button b) {
		menuItems.put(view, b);
	}

	public HashMap<Class<? extends ComponentContainer>, Button> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(HashMap<Class<? extends ComponentContainer>, Button> menuItems) {
		this.menuItems = menuItems;
	}
	

	
}
