package de.cas.vaadin.thelibrary.ui.view.content.readerview;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Reader;

public class ReaderGrid extends Grid<Reader> {

    private MasterController masterController;
    private ListDataProvider<Reader> dataProvider;
    private Provider<Button> buttonProvider;

    private ReaderEditWindow readerEditwindow;

    @Inject
    public ReaderGrid(MasterController masterController,
                    Provider<Button> buttonProvider,
                    ReaderEditWindow readerEditwindow){
        this.masterController = masterController;

        this.buttonProvider = buttonProvider;
        this.readerEditwindow = readerEditwindow;
        AppEventBus.register(this);
        setBeanType(Reader.class);
        dataProvider = new ListDataProvider<>(masterController.getReaderController().getItems());
        configure();
    }

    private void configure(){
        setColumns("name", "address", "email", "phoneNumber", "id");

        setSelectionMode(SelectionMode.SINGLE);
        setStyleName("grid-overall");
        setDataProvider(dataProvider);
        setResponsive(true);
        //Selector for filtering by state

        //Adding the selector
        addHeaderRowAt(1);
        addComponentColumn(this::buildEditButton).setCaption("Edit");
        addComponentColumn(this::buildDeleteButton).setCaption("Delete");

        setSizeFull();
    }


    private Button buildEditButton(Reader reader){
        Button edit = buttonProvider.get();
        edit.addStyleName(ValoTheme.BUTTON_SMALL);
        edit.setIcon(VaadinIcons.PENCIL);
        edit.addClickListener(e-> {
            AppEventBus.post(new AppEvent.CloseOpenedWindowsEvent());
            AppEventBus.post(new AppEvent.EditObjectEvent());
            readerEditwindow.setReader(reader);
            readerEditwindow.createWindow();
        });
        return edit;
    }

    private Button buildDeleteButton(Reader reader){
        Button del = buttonProvider.get();
        del.addStyleName(ValoTheme.BUTTON_SMALL);
        del.setIcon(VaadinIcons.TRASH);
        del.addClickListener(e->{
            masterController.getReaderController().delete(reader);
            AppEventBus.post(new AppEvent.EditObjectEvent());
        });
        return del;
    }

    //Subscribe to events from other classes, to refresh the grid
    @Subscribe
    private void refreshGrid(final AppEvent.EditObjectEvent e){
        dataProvider = new ListDataProvider<>(masterController.getReaderController().getItems());
        setDataProvider(dataProvider);
    }

    @Subscribe
    private void searchFilter(final AppEvent.SearchFilterEvent e){
        dataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
                reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
                reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
                reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));
    }


}
