package de.cas.vaadin.thelibrary.ui.builder;

import java.util.ArrayList;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class SideMenuBuilder {

	private Label title = new Label();
	private ArrayList<Button> menuItems;
	private CssLayout menu, viewContainer;
	
	public SideMenuBuilder(String title, ArrayList<String> menuItems) {
		this.title.setValue(title);
		this.menuItems = createMenuButtons(menuItems);
		menu = new CssLayout(this.title);
		for(Button b : this.menuItems) {
			menu.addComponent(b);
		}
		menu.addStyleName(ValoTheme.MENU_ROOT);		
		viewContainer = new CssLayout();

	}
	
	public HorizontalLayout build() {
		HorizontalLayout mainLayout = new HorizontalLayout(this.menu, this.viewContainer);
		mainLayout.setSizeFull();
		return mainLayout;
	}
	
	private ArrayList<Button> createMenuButtons(ArrayList<String> menuItems) {
		ArrayList<Button> menuButtons = new ArrayList<Button>();
		for(String s : menuItems) {
			Button b = new Button(s);
			menuButtons.add(b);
		}
		return menuButtons;
	}

	
	/*GETTERS AND SETTERS*/
	
	public String getTitle() {
		return title.getValue();
	}

	public void setTitle(String title) {
		this.title.setValue(title);;
	}

	public ArrayList<Button> getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(ArrayList<String> menuItems) {
		for(String s : menuItems) {
			Button b = new Button(s);
			this.menuItems.add(b);
		}
	}
	
	
	public CssLayout getViewContainer() {
		return viewContainer;
	}

	public void setViewContainer(CssLayout viewContainer) {
		this.viewContainer = viewContainer;
	}

	public void setMenuButtonStyle(String... styles) {
		for(Button b : menuItems) {
			b.addStyleNames(styles);
			
		}
	}

	public void setTitleStyle(String style) {
		this.title.addStyleName(style);
	}
	
}
