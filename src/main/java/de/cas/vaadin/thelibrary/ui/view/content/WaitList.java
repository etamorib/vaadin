package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.WaitlistController;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class WaitList implements CreateContent {
	
	private final String name = "Waitlists";
	private WaitlistController controller = new WaitlistController();

	private Grid<Waitlist> grid = new Grid<>(Waitlist.class);
	private VerticalLayout layout;
	private ListDataProvider<Waitlist> dataProvider ;

	@Override
	public Component buildContent() {
		layout = new VerticalLayout();
		Label title = new Label("Waitlist");
		title.setStyleName(ValoTheme.LABEL_H1);
		layout.addComponents(title,  buildGrid());
		return layout;
	}
	
	private Component buildGrid() {
		dataProvider = new ListDataProvider<>(controller.getItems());
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setSizeFull();
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		
		return grid;
	}
	
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Resource menuIcon() {
		return VaadinIcons.TIMER;
	}

}
