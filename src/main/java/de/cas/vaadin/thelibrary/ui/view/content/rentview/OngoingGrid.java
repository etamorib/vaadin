package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.vaadin.data.provider.ListDataProvider;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Rent;

public class OngoingGrid extends RentGrid {
    private MasterController masterController;

    @Inject
    public OngoingGrid(MasterController masterController) {
        super(masterController);
        setDataProviderImpl();
    }

    @Override
    protected void setDataProviderImpl() {
        ListDataProvider<Rent> listDataProvider = new ListDataProvider<>(super.ongoingList);
        setDataProvider(listDataProvider);
    }
}
