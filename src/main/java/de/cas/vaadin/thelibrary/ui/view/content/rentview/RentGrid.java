package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.vaadin.ui.Grid;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Rent;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class RentGrid extends Grid<Rent>  {

    protected ArrayList<Rent> ongoingList = new ArrayList<>();
	protected ArrayList<Rent> lateList = new ArrayList<>();
	private MasterController masterController;

	@Inject
	public RentGrid(MasterController masterController){

        this.masterController = masterController;
        setBeanType(Rent.class);
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
		setSelectionMode(SelectionMode.MULTI);
		setColumns("readerId", "bookId", "rentTime", "returnTime");
		setSizeFull();
		setStyleName("grid-overall");
		//grid.setDataProvider(dataProvider);
	}

	protected abstract void setDataProviderImpl();


}
