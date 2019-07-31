package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.vaadin.navigator.Navigator;
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

//Abstract?

public class MainView extends HorizontalLayout {

	SideMenuBuilder menu = SideMenuBuilder.Instance();
	CssLayout content ;
	
	public MainView() {
		setSizeFull();
		addComponent(new SideMenuBuilder());
		content = new CssLayout();
		content.addComponent(new Label("asd"));
		addComponent(content);
		
		AppEventBus.register(this);
		//updateContent(getClass().toString());
		
	}
	
	//Ha az event.getContainer null, akkor a MainView hivodik meg, amugy pedig az update
	
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		System.out.println(event.getContainer());
		if(!(event.getContainer().toString() == null))
			updateContent(event.getContainer().toString());
	}

	private void updateContent(String str) {
		content.addComponent(new BooksView());
		
		
	}
	
	
	
	
}
