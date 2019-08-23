package de.cas.vaadin.thelibrary.ui.view.content.waitlistview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;

public class WaitListGrid extends Grid<Waitlist> {

    private MasterController masterController;
    private ListDataProvider<Waitlist> dataProvider;
    private Provider<Button> buttonProvider;

    @Inject
    public WaitListGrid(MasterController masterController,
                        Provider<Button> buttonProvider)
    {
        this.masterController = masterController;
        this.buttonProvider = buttonProvider;
        setBeanType(Waitlist.class);
        configure();
    }

    private void configure() {
        setColumns( "bookId", "readerId", "waitDate", "requestDate");
        setSelectionMode(SelectionMode.SINGLE);
        setStyleName("grid-overall");
        addDataProvider();
        setResponsive(true);
        //Selector for filtering by state

        //Adding the selector
        addHeaderRowAt(1);
        addComponentColumn(this::buildDeleteButton).setCaption("Delete");

        setSizeFull();
    }

    private void addDataProvider() {
        dataProvider = new ListDataProvider<>(masterController.getWaitlistController().getItems());
        setDataProvider(dataProvider);
    }

    private Button buildDeleteButton(Waitlist waitlist){
        Button del = buttonProvider.get();
        del.setIcon(VaadinIcons.TRASH);
        del.addClickListener(e->{
            masterController.getWaitlistController().delete(waitlist);
            addDataProvider();
        });

        return del;
    }


}
