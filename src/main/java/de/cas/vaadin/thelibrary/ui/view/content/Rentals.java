package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.VerticalLayout;

import de.cas.vaadin.thelibrary.controller.RentController;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


public class Rentals implements CreateContent {

	private RentController controller = new RentController();
	
	private final String name = "Rents";
	private Grid<Rent> grid = new Grid<>(Rent.class);
	private VerticalLayout layout;
	private ListDataProvider<Rent> dataProvider ;
	
	
	@Override
	public Component buildContent() {
		layout = new VerticalLayout();
		layout.setSizeFull();
		
		layout.addComponent(buildGrid());
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
		return VaadinIcons.REPLY_ALL;
	}
	
	

}
