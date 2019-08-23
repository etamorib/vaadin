package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.ui.Button;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;

public class OngoingGrid extends RentGrid {

    @Inject
    public OngoingGrid(MasterController masterController, Provider<Button> buttonProvider) {
        super(masterController, buttonProvider);
        setCaption("Ongoing rent(s)");
        setDataProviderImpl();
    }


    @Override
    protected void setDataProviderImpl() {
        super.updateLists();
        ListDataProvider<Rent> listDataProvider = new ListDataProvider<>(super.ongoingList);
        setDataProvider(listDataProvider);
    }
}
