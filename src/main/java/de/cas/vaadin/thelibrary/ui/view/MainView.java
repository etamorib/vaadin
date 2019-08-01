package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.content.BooksView;

//Abstract?

public class MainView extends HorizontalLayout {

	private SideMenuBuilder menu = SideMenuBuilder.Instance();
	private VerticalLayout content ;
	private Content c;
	
	public MainView() {
		removeAllComponents();
		setSizeFull();
		
		addComponent(menu);
		content = new VerticalLayout();
		content.addComponent(new Label("asd"));
		addComponent(content);
		setExpandRatio(menu, 1.5f);
		setExpandRatio(content, 8.5f);
		
		AppEventBus.register(this);		
	}
	
	
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		
		c = new Content(event.getContainer());
		content.removeAllComponents();
		content.addComponent(c.createContent());

		
	}

	
	
	
	
}
