package de.cas.vaadin.thelibrary.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.ui.view.wrapper.AbstractViewWrapper;

public class SideMenuBuilder {
	private HashMap<Button, AbstractViewWrapper> menuItems = new HashMap<>();
	private CssLayout menu, viewContainer;
	private Label title = new Label("Library");
	Button menuButton;
	
	@SafeVarargs
	public SideMenuBuilder(AbstractViewWrapper...menuItems) {
		createMenuButtons(menuItems);
		//menu = new CssLayout(title);
		//this.menuItems.forEach((k,v)->menu.addComponent(k));
		//menu.addStyleName(ValoTheme.MENU_ROOT);		
		//viewContainer = new CssLayout();

	}

	
	public HorizontalLayout buildLayout() {
		HorizontalLayout builtLayout = new HorizontalLayout(this.menu, this.viewContainer);
		builtLayout.setSizeFull();
		return builtLayout;
	}
	
	public void createMenuButtons(AbstractViewWrapper... menuItems) {
		for(AbstractViewWrapper avw : menuItems) {
			String buttonText;
			if(avw.name().equals("")) {
				buttonText="Default";
				
			}else {
				buttonText=avw.name();
			}
			menuButton = new Button(buttonText);	
			menuButton.addStyleNames(ValoTheme.BUTTON_LINK, ValoTheme.MENU_ITEM);
			this.menuItems.put(menuButton, avw);
			
		}
		
	}
	
	

	
	/*GETTERS AND SETTERS*/
	
	public String getTitle() {
		return title.getValue();
	}

	public void setTitle(String title) {
		this.title.setValue(title);;
	}


	public CssLayout getViewContainer() {
		return viewContainer;
	}

	public void setViewContainer(CssLayout viewContainer) {
		this.viewContainer = viewContainer;
	}

	public void setMenuButtonStyle(String... styles) {
		
		this.menuItems.forEach((k,v)->k.addStyleNames(styles));
	}

	public void setTitleStyle(String style) {
		this.title.addStyleName(style);
	}


	public HashMap<Button, AbstractViewWrapper> getMenuItems() {
		return menuItems;
	}


	public void setMenuItems(HashMap<Button, AbstractViewWrapper> menuItems) {
		this.menuItems = menuItems;
	}



	
}
