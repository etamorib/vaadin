package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.vaadin.ui.HorizontalLayout;

import de.cas.vaadin.thelibrary.CASTheLibraryApplication;
import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.modules.AppModule;
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
	private BooksView booksView;
	private Provider<SideMenuBuilder> provider;

	@Inject
	public MainView(SideMenuBuilder menu, BooksView booksView, Provider<SideMenuBuilder> provider) {
		this.provider = provider;
		this.menu = menu;
		this.booksView = booksView;
		setSizeFull();
		AppEventBus.register(this);
		addComponent(menu);


		menu.setContent(booksView.buildContent());
	}
	
	
	/**
	 * This method makes use of the Strategy design pattern, so
	 * it changes the Content component according to the object
	 * given to the event parameter
	 * @param ChangeViewEvent event
	 */
	@Subscribe
	private void changeContentEvent(final ChangeViewEvent event) {
		System.out.println("EVENT: "+event.getContainer());
		removeAllComponents();
		addComponent(menu);
		Content c = new Content(event.getContainer());
		/*Set the content to the Content's createContent method
		 * It call the right buildContent method
		*/
		menu.setContent(c.createContent());
	}


	
}
