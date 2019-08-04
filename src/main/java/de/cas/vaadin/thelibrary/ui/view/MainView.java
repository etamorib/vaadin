package de.cas.vaadin.thelibrary.ui.view;

import org.vaadin.teemusa.sidemenu.SideMenu;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;



/**
 * @author mate.biro
 * A component container class which contains two
 * main components: <b>SideMenu<b> and <b>Content<b>.
 * The SideMenu is fixed, it does not change.
 * The Content changes according to the selected view
 * in the SideMenu.
 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

	private SideMenuBuilder menu = new SideMenuBuilder();
	private Content c;
	
	public MainView() {
		setSizeFull();
		AppEventBus.register(this);
		addComponent(menu.getSideMenu());
	}
	
	
	/**
	 * @param ChangeViewEvent event
	 * This method makes use of the Strategy design pattern, so
	 * it changes the Content component according to the object
	 * given to the event parameter
	 */
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		removeAllComponents();
		//New menu always needed to refresh the views
		//in sidemenubuilder constructor
		menu = new SideMenuBuilder();
		addComponent(menu.getSideMenu());
		c = new Content(event.getContainer());
		menu.getSideMenu().setContent(c.createContent());


		
	}

	
	
	
	
}
