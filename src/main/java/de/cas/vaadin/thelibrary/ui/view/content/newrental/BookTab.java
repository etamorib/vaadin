package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;

import java.util.Set;

public class BookTab extends VerticalLayout {

    private Grid<Book> bookGrid;
    private ListDataProvider<Book> listDataProvider;
    private Provider<TextField> textFieldProvider;
    private Provider<Label> labelProvider;
    private Provider<Grid<Book>> bookGridProvider;
    private NativeSelect<BookState> state;
    private Provider<NativeSelect<BookState>> stateProvider;
    private Label counter;
    private MasterController masterController;
    private static final int MAX_SELECTED = 5;


    @Inject
    public BookTab(Provider<TextField> textFieldProvider,
                   Provider<Label> labelProvider,
                   Provider<Grid<Book>> bookGridProvider,
                   Provider<NativeSelect<BookState>> stateProvider,
                   MasterController masterController
                   )
    {
        this.textFieldProvider = textFieldProvider;
        this.labelProvider = labelProvider;
        this.bookGridProvider = bookGridProvider;
        this.stateProvider = stateProvider;
        this.masterController = masterController;
        listDataProvider = new ListDataProvider<>(masterController.getBookController().getItems());
        AppEventBus.register(this);
        setCaption("Books");

        addComponents(searchField(),buildGrid(), selectedCounter());
    }

    private Component buildGrid(){
        initialFilter();
        bookGrid = bookGridProvider.get();
        bookGrid.setSizeFull();
        addDataProvider();
        state = stateProvider.get();
        state.setStyleName("dropdown-select");
        state.setItems(BookState.Available, BookState.Borrowed);
        addStateFilter();
        bookGrid.getHeaderRow(0).getCell("state").setComponent(state);
        bookGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        bookGrid.setStyleName("grid-overall");
        bookGrid.addSelectionListener(e->{
           updateCounter();
           AppEventBus.post(new AppEvent.SelectedBooksEvent(e.getAllSelectedItems()));
        });
        return bookGrid;
    }

    private void addDataProvider(){
        listDataProvider = new ListDataProvider<>(masterController.getBookController().getItems());
        bookGrid.setDataProvider(listDataProvider);
    }

    private void addStateFilter() {
        state.addSelectionListener(e -> {
            //If there is something selected
            if (e.getValue() != null) {
                listDataProvider.setFilter(book -> book.getState() == e.getValue());
            }
            //Else just show everything
            else {
                listDataProvider.setFilter(book->book.getState()==BookState.Borrowed || book.getState() == BookState.Available);
            }
        });

    }

    private Component selectedCounter(){
        counter = labelProvider.get();
        counter.setValue(bookGrid.getSelectedItems().size()+ " book(s) is selected");
        return counter;
    }

    private void updateCounter(){
        if(bookGrid.getSelectedItems().size()>MAX_SELECTED){
            bookGrid.deselect(bookGrid.getSelectedItems().iterator().next());
            Notification.show("5 books can be selected at once!");
        }
        counter.setValue(bookGrid.getSelectedItems().size()+ " book(s) is selected");
    }

    //Set initial filter
    private void initialFilter(){
        //Filter
        listDataProvider.setFilter(book->book.getState() == BookState.Available || book.getState()==BookState.Borrowed);
    }

    private Component searchField(){
        TextField search;
        search  = textFieldProvider.get();
        search.setIcon(VaadinIcons.SEARCH);
        search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e->{
            //Filter for both title and author
            listDataProvider.setFilter(book -> (book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
                    book.getTitle().toLowerCase().contains(e.getValue().toLowerCase())) &&
                    book.getState()!=BookState.Deleted);

        });
        return search;
    }

    @Subscribe
    private void updateGrid(final AppEvent.EditObjectEvent e){
        addDataProvider();
    }

}
