package de.cas.vaadin.thelibrary.ui.view.content;

import org.vaadin.ui.NumberField;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.RentController;
import de.cas.vaadin.thelibrary.controller.WaitlistController;
import de.cas.vaadin.thelibrary.event.AppEvent.NotificationEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;


/**This class is the view of the rentals
 * @author mate.biro
 *
 */
public class Rentals implements CreateContent {

	private RentController controller = new RentController();
	private BookController bookController = new BookController();
	private WaitlistController waitlistController = new WaitlistController();
	
	private final String name = "Rents";
	private Grid<Rent> grid = new Grid<>(Rent.class);
	private VerticalLayout layout;
	private ListDataProvider<Rent> dataProvider ;
	
	
	@Override
	public Component buildContent() {
		AppEventBus.register(this);
		layout = new VerticalLayout();
		Label title = new Label("Current rents");
		title.setStyleName(ValoTheme.LABEL_H1);
		layout.addComponents(title, buildButtons(), buildGrid());
		return layout;
	}
	
	private Component buildGrid() {
		grid.setCaption("Ongoing rents");
		dataProvider = new ListDataProvider<>(controller.getItems());
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("readerId", "bookId", "rentTime", "returnTime");
		grid.setSizeFull();
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		
		return grid;
	}
	
	private Component buildButtons() {
		HorizontalLayout buttons = new HorizontalLayout();
		
		//Del button
		Button del = new Button();
		del.setIcon(VaadinIcons.TRASH);
		del.setStyleName("header-button");
		del.setDescription("Delete selected items");
		del.addClickListener(e->{
			
			
			//If a book is deleted, which is on the waitlist, it automatically goes to rent
			//else it is just deleted
			for(Rent r : grid.getSelectedItems()) {
				Book b = bookController.findById(r.getBookId());
				b.setState(BookState.Available);
				bookController.update(b);
				
				for(Waitlist w : waitlistController.getItems()) {
					if(w.getBookId().intValue()==b.getId().intValue()) {
						AppEventBus.post(new NotificationEvent(b.getTitle() +" by "+b.getAuthor() +" is now available for waitlisters"));
					}
				}
			}
			controller.delete(grid.getSelectedItems());
			dataProvider = new ListDataProvider<>(controller.getItems());
			grid.setDataProvider(dataProvider);

		});
		//Search for id
		NumberField search  = new NumberField();
		search.setDecimalAllowed(false);
		search.setNegativeAllowed(false);
		search.setGroupingUsed(false);
		search.setIcon(VaadinIcons.SEARCH);	
	    search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

		search.setValueChangeMode(ValueChangeMode.EAGER);
		search.addValueChangeListener(e->{
			//To prevent NumberFormatException
			if(!e.getValue().equals("")) {
				dataProvider.setFilter(rent->rent.getBookId()==Integer.parseInt(e.getValue())||
											rent.getReaderId()==Integer.parseInt(e.getValue()));
			}else {
				dataProvider.clearFilters();
			}
			
		});
			
		buttons.addComponents(del, search);
		return buttons;
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
