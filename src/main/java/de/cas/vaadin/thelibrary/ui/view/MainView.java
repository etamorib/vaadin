package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.vaadin.ui.HorizontalLayout;

import de.cas.vaadin.thelibrary.CASTheLibraryApplication;
import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;
import de.cas.vaadin.thelibrary.ui.view.content.BooksView;



/**
 *  A component container class which contains two
 * main components: <b>SideMenu<b> and <b>Content<b>.
 * The SideMenu is fixed, it does not change.
 * The Content changes according to the selected view
 * in the SideMenu.
 * @author mate.biro

 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

	private SideMenuBuilder menu;
	private Content c;
	private BooksView startView = new BooksView();
	public MainView() {
		setSizeFull();
		AppEventBus.register(this);
		menu = CASTheLibraryApplication.getInjector().getInstance(SideMenuBuilder.class);
		addComponent(menu.getSideMenu());

		/*At the very beginning, lets show the
		 * BooksView.
		 * Anything else could be shown too, like a welcome text
		 * */
		menu.getSideMenu().setContent(startView.buildContent());
	}
	
	
	/**
	 * This method makes use of the Strategy design pattern, so
	 * it changes the Content component according to the object
	 * given to the event parameter
	 * @param ChangeViewEvent event
	 */
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		removeAllComponents();
		/*New menu always needed to refresh the views
		in sidemenubuilder constructor, so new SideMenuBuilder is created
		every time*/
		//menu = new SideMenuBuilder();
		addComponent(menu.getSideMenu());
		c = new Content(event.getContainer());
		/*Set the content to the Content's createContent method
		 * It call the right buildContent method
		*/
		menu.getSideMenu().setContent(c.createContent());
	}


	
}
