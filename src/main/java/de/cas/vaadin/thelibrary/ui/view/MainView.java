package de.cas.vaadin.thelibrary.ui.view;

import com.google.inject.Inject;
import com.vaadin.ui.HorizontalLayout;
import de.cas.vaadin.thelibrary.ui.builder.SideMenuBuilder;



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


	@Inject
	public MainView(SideMenuBuilder menu) {
		this.menu = menu;
		setSizeFull();
		addComponent(menu);
	}

	public SideMenuBuilder getMenu(){
		return this.menu;
	}

}
