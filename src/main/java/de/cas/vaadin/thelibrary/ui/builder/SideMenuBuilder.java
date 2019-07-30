package de.cas.vaadin.thelibrary.ui.builder;

import java.util.ArrayList;
import java.util.HashMap;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class SideMenuBuilder {
	private HashMap<Button, Class<? extends ComponentContainer>> menuItems = new HashMap<>();
	private CssLayout menu, viewContainer;
	private Label title = new Label("Library");
	
	@SafeVarargs
	public SideMenuBuilder(Class<? extends ComponentContainer>... menuItems) {
		createMenuButtons(menuItems);
		menu = new CssLayout(title);
		this.menuItems.forEach((k,v)->menu.addComponent(k));
		menu.addStyleName(ValoTheme.MENU_ROOT);		
		viewContainer = new CssLayout();

	}

	
	public HorizontalLayout buildLayout() {
		HorizontalLayout builtLayout = new HorizontalLayout(this.menu, this.viewContainer);
		builtLayout.setSizeFull();
		return builtLayout;
	}
	
	private void createMenuButtons(Class<? extends ComponentContainer>... menuItems) {
		for(Class<? extends ComponentContainer> c : menuItems) {
			
			//TODO: INTERFACET IRNI EGY OLYAN METODUSRA AMI VISSZAADJA AZ OSZTÁLYBAN
			//TÁROLD NEVET
			Button b = new Button(c.getName());
			this.menuItems.put(b, c);
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
	
}
