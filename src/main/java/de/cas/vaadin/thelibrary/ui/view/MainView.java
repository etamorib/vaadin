package de.cas.vaadin.thelibrary.ui.view;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;

import com.vaadin.ui.UI;
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

	@Inject
	public MainView(SideMenuBuilder menu, BooksView booksView) {
		this.menu = menu;
		this.booksView = booksView;
		setSizeFull();
		addComponent(menu);
		menu.setContent(booksView.buildContent());
	}

	public SideMenuBuilder getMenu(){
		return this.menu;
	}



	
}
