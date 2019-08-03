package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
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
	private VerticalLayout content ;
	private HorizontalLayout layout = new HorizontalLayout();
	private Content c;
	
	public MainView() {
		removeAllComponents();
		setSizeFull();
		System.out.println("MAIN VIEW");
		layout.addComponent(menu);
		layout.setHeight("100%");
		addComponent(layout);
		content = new VerticalLayout();
		content.addComponent(new Label("asd"));
		addComponent(content);
		setExpandRatio(layout, 1.5f);
		setExpandRatio(content, 8.5f);
		
		AppEventBus.register(this);		
	}
	
	
	/**
	 * @param ChangeViewEvent event
	 * This method makes use of the Strategy design pattern, so
	 * it changes the Content component according to the object
	 * given to the event parameter
	 */
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		layout.removeAllComponents();
		layout.addComponent(new SideMenuBuilder());
		c = new Content(event.getContainer());
		content.removeAllComponents();
		content.addComponent(c.createContent());

		
	}

	
	
	
	
}
