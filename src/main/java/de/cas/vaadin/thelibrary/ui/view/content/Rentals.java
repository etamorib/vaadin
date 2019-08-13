package de.cas.vaadin.thelibrary.ui.view.content;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

import javax.mail.MessagingException;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.ReaderController;
import de.cas.vaadin.thelibrary.controller.RentController;
import de.cas.vaadin.thelibrary.controller.WaitlistController;
import de.cas.vaadin.thelibrary.event.AppEvent.NotificationEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
import de.cas.vaadin.thelibrary.utils.EmailSender;


/**This class is the view of the rentals
 * @author mate.biro
 *
 */
public class Rentals implements CreateContent {

	private RentController controller = new RentController();
	private BookController bookController = new BookController();
	private WaitlistController waitlistController = new WaitlistController();
	private ReaderController readerController = new ReaderController();
	
	private final String name = "Rents";
	private Grid<Rent> grid = new Grid<>(Rent.class);
	private Grid<Rent> late = new Grid<>(Rent.class);
	
	//Lists for ongoing and late rents
	private ArrayList<Rent> ongoingList = new ArrayList<>();
	private ArrayList<Rent> lateList = new ArrayList<>();

	private VerticalLayout layout;
	private ListDataProvider<Rent> dataProvider ;
	private ListDataProvider<Rent> lateDataProvider ;

	
	
	@Override
	public Component buildContent() {
		AppEventBus.register(this);
		layout = new VerticalLayout();
		Label title = new Label("Current rents");
		title.setStyleName(ValoTheme.LABEL_H1);
		updateLists();
		layout.addComponents(title, buildButtons(), buildGrid(), 
				lateList.size()>0?lateGrid(): new Label("There are no outdated rents"));
		return layout;
	}
	
	//Outdated and ongoing rents are stored in different lists
	//This method updates them from the database
	private void updateLists() {
		ongoingList.clear();
		lateList.clear();
		controller.getItems().forEach(e->{
			if(e.getReturnTime().isBefore(LocalDate.now()) || e.getReturnTime().isEqual(LocalDate.now())) {
				lateList.add(e);
			}
			if(e.getReturnTime().isAfter(LocalDate.now())) {
				ongoingList.add(e);
			}
		});
	
	}
	
	//Building the ongoing rents grid
	private Component buildGrid() {
		grid.setCaption("Ongoing rents");
		updateLists();
		dataProvider = new ListDataProvider<>(ongoingList);
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setColumns("readerId", "bookId", "rentTime", "returnTime");
		grid.setSizeFull();
		grid.setStyleName("grid-overall");
		grid.setDataProvider(dataProvider);
		
		return grid;
	}
	
	//Build a button to send email
	private Button emailSender() {
		Button sendEmail = new Button("Send email to notify late readers");
		sendEmail.setIcon(VaadinIcons.MAILBOX);
		sendEmail.setStyleName(ValoTheme.BUTTON_DANGER);
		sendEmail.addClickListener(e->{
			//For every reader that is in the latelist
			updateLists();
			for(Rent r: lateList) {
				Reader reader = readerController.findById(r.getReaderId());
				EmailSender sender = new EmailSender(reader);
				try {
					sender.send();
					Notification.show("Emails have been sent");
				}catch (MessagingException ex) {
					Notification.show("Sending was unsuccesful!");
					ex.printStackTrace();
				}
			}
		});
		return sendEmail;
	}
	
	//Build the grid for outdated rents
	private Component lateGrid() {
		VerticalLayout layout = new VerticalLayout();

		updateLists();
		lateDataProvider = new ListDataProvider<Rent>(lateList);
		late.setColumns("readerId", "bookId", "rentTime", "returnTime");
		late.setSelectionMode(SelectionMode.MULTI);
		late.setSizeFull();
		late.setStyleName("grid-overall");
		late.setDataProvider(lateDataProvider);
		
		Label title = new Label("Outdated rents");
		title.setStyleName(ValoTheme.LABEL_H3);
		layout.addComponents(title, emailSender(),late);
		return layout;
	}
	
	//Deletes items based on a set
	//So deleting from both grid is easy, based on the parameter
	private void deleteSelectedItems(Set<Rent> set) {
		for(Rent r : set) {
			Book b = bookController.findById(r.getBookId());
			b.setState(BookState.Available);
			bookController.update(b);
			
			for(Waitlist w : waitlistController.getItems()) {
				if(w.getBookId().intValue()==b.getId().intValue()) {
					System.out.println("NOTIFICATION");
					AppEventBus.post(new NotificationEvent(b.getTitle() +" by "+b.getAuthor() +" is now available for waitlisters"));
				}
			}
		}
		controller.delete(set);
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
			deleteSelectedItems(grid.getSelectedItems());
			deleteSelectedItems(late.getSelectedItems());
			
			updateLists();
			lateDataProvider = new ListDataProvider<>(lateList);
			dataProvider = new ListDataProvider<>(ongoingList);
			grid.setDataProvider(dataProvider);
			late.setDataProvider(lateDataProvider);

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
