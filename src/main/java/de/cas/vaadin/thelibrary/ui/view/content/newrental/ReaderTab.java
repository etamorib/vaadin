package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Reader;

public class ReaderTab extends VerticalLayout  {

    private Provider<Grid<Reader>> gridProvider;
    private Grid<Reader> readerGrid;
    private Provider<TextField> textFieldProvider;
    private MasterController masterController;
    private ListDataProvider<Reader> listDataProvider;

    @Inject
    public ReaderTab(Provider<Grid<Reader>> gridProvider,
                     Provider<TextField> textFieldProvider, MasterController masterController){
        this.gridProvider = gridProvider;
        this.textFieldProvider = textFieldProvider;
        this.masterController = masterController;
        setCaption("Readers");
        addComponents(searchField(), buildGrid());
    }

    private Component searchField() {
        TextField search;
        search  = textFieldProvider.get();
        search.setIcon(VaadinIcons.SEARCH);
        search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e->{
            //Filter for both title and author
            listDataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));

        });
        return search;
    }

    private Component buildGrid(){
        readerGrid = gridProvider.get();
        listDataProvider = new ListDataProvider<>(masterController.getReaderController().getItems());
        readerGrid.setDataProvider(listDataProvider);
        readerGrid.setSizeFull();
        readerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        readerGrid.addSelectionListener(e->{
            AppEventBus.post(new AppEvent.SelectedReaderEvent(e.getAllSelectedItems().iterator().next()));
        });
        return readerGrid;
    }


}
