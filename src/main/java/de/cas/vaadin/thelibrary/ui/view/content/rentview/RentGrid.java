package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.*;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class RentGrid extends Grid<Rent>  {

    protected ArrayList<Rent> ongoingList = new ArrayList<>();
    protected Provider<Button> buttonProvider;
	protected ArrayList<Rent> lateList = new ArrayList<>();
	protected MasterController masterController;

	@Inject
	public RentGrid(MasterController masterController, Provider<Button> buttonProvider){
		this.buttonProvider = buttonProvider;
        this.masterController = masterController;
        setBeanType(Rent.class);
		setCaptionAsHtml(true);
		setSizeFull();
        buildGrid();
    }

    protected void updateLists() {
		ongoingList.clear();
		lateList.clear();
		masterController.getRentController().getItems().forEach(e->{
			if(e.getReturnTime().isBefore(LocalDate.now()) || e.getReturnTime().isEqual(LocalDate.now())) {
				lateList.add(e);
			}
			if(e.getReturnTime().isAfter(LocalDate.now())) {
				ongoingList.add(e);
			}
		});
	}

	protected void buildGrid() {
		//setCaption("Ongoing rents");
		updateLists();
		//dataProvider = new ListDataProvider<>(ongoingList);
		setSelectionMode(SelectionMode.SINGLE);
		setColumns("readerId", "bookId", "rentTime", "returnTime");
		setSizeFull();
		setStyleName("grid-overall");
		addComponentColumn(this::buildDeleteButton).setCaption("Delete");

		//grid.setDataProvider(dataProvider);
	}

	protected  Button buildDeleteButton(Rent rent){
		Button del = buttonProvider.get();
		del.setIcon(VaadinIcons.TRASH);
		del.addClickListener(e->{
			Book b = masterController.getBookController().findById(rent.getBookId());
			if(b.getNumber()<1) {
				b.setState(BookState.Available);
				b.setNumber(b.getNumber()+1);
			}else{
				b.setNumber(b.getNumber()+1);
			}
			masterController.getBookController().update(b);
			masterController.getRentController().delete(rent);
			sendNotification(b);
			setDataProviderImpl();
		});

		return del;
	}

	private void sendNotification(Book b) {
		for(Waitlist w : masterController.getWaitlistController().getItems()) {
			if(w.getBookId().intValue()==b.getId().intValue()) {
				System.out.println("NOTIFICATION");
				AppEventBus.post(new AppEvent.NotificationEvent(b.getTitle() +" by "+b.getAuthor() +" is now available for waitlisters"));
			}
		}
	}

	protected abstract void setDataProviderImpl();


}
