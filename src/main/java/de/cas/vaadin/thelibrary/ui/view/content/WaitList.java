package de.cas.vaadin.thelibrary.ui.view.content;

import com.google.inject.Inject;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

import java.time.LocalDate;

/**This class is the view of the waitlist
 * @author mate.biro
 *
 */
public class WaitList implements CreateContent {
	
	private final String name = "Waitlists";
	private Grid<Waitlist> grid = new Grid<>(Waitlist.class);
	private VerticalLayout layout;
	private ListDataProvider<Waitlist> dataProvider ;

	private MasterController masterController;
	@Inject
	public WaitList(MasterController masterController){
		this.masterController = masterController;
	}

	@Override
	public Component buildContent() {
		layout = new VerticalLayout();
		Label title = new Label("Waitlist");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		Button rent = new Button();
		rent.setIcon(VaadinIcons.TRASH);
		rent.setStyleName("header-button");
		addRentClickListener(rent);
		
		layout.addComponents(title, rent, buildGrid());
		return layout;
	}

	private void addRentClickListener(Button rent) {
		rent.addClickListener(e->{
			if(grid.getSelectedItems().size()>0) {
				Book b = masterController.getBookController().findById(grid.getSelectedItems().iterator().next().getBookId());
				if(b.getState() == BookState.Available) {
					masterController.getWaitlistController().delete(grid.getSelectedItems());
					masterController.getRentController().add(new Rent(LocalDate.now(), LocalDate.now().plusMonths(2), b.getId(),
							grid.getSelectedItems().iterator().next().getReaderId()));
					b.setState(BookState.Borrowed);
					masterController.getBookController().update(b);
				}else {
					masterController.getWaitlistController().delete(grid.getSelectedItems());
				}
				dataProvider = new ListDataProvider<>(masterController.getWaitlistController().getItems());
				grid.setDataProvider(dataProvider);
			}
		});
	}

	private Component buildGrid() {
		dataProvider = new ListDataProvider<>(masterController.getWaitlistController().getItems());
		grid.setSelectionMode(SelectionMode.SINGLE);
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
