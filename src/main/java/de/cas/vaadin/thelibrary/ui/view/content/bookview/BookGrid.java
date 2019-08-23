package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Rent;

public class BookGrid extends Grid<Book> {
    private MasterController masterController;
    private ListDataProvider<Book> dataProvider;
    private Provider<Button> buttonProvider;
    private Provider<NativeSelect<BookState>> bookState;
    private NativeSelect<BookState> state;
    private BookEditWindow bookEditWindow;

    @Inject
    public BookGrid(MasterController masterController, Provider<NativeSelect<BookState>> bookState,
                    Provider<Button> buttonProvider,
                    BookEditWindow bookEditWindow){
        this.masterController = masterController;
        this.bookState = bookState;
        this.buttonProvider = buttonProvider;
        this.bookEditWindow = bookEditWindow;
        AppEventBus.register(this);
        setBeanType(Book.class);
        dataProvider = new ListDataProvider<>(masterController.getBookController().getItems());
        configure();
    }

    private void configure(){
        setColumns("id","author","title", "category","year", "state", "number");
        setSelectionMode(SelectionMode.SINGLE);
        setStyleName("grid-overall");
        setDataProvider(dataProvider);
        setResponsive(true);
        //Selector for filtering by state
        state = bookState.get();
        state.setStyleName("dropdown-select");
        state.setItems(BookState.Available, BookState.Borrowed, BookState.Deleted);
        addStateFilter();
        //Adding the selector
        addHeaderRowAt(1);
        getHeaderRow(1).getCell("state").setComponent(state);
        addComponentColumn(this::buildEditButton).setCaption("Edit");
        addComponentColumn(this::buildDeleteButton).setCaption("Delete");

        setSizeFull();
    }

    private void addStateFilter(){
        state.addSelectionListener(e->{
            //If there is something selected
            if(e.getValue()!=null) {
                dataProvider.setFilter(book -> book.getState() ==e.getValue());
            }
            //Else just show everything
            else {
                dataProvider.clearFilters();
            }
        });
    }

    private Button buildEditButton(Book book){
        Button edit = buttonProvider.get();
        edit.addStyleName(ValoTheme.BUTTON_SMALL);
        edit.setIcon(VaadinIcons.PENCIL);
        edit.addClickListener(e-> {
            AppEventBus.post(new AppEvent.CloseOpenedWindowsEvent());
            AppEventBus.post(new AppEvent.EditObjectEvent());
            bookEditWindow.setBook(book);
            bookEditWindow.createWindow();
        });
        return edit;
    }

    private Button buildDeleteButton(Book book){
        Button del = buttonProvider.get();
        del.addStyleName(ValoTheme.BUTTON_SMALL);
        del.setIcon(VaadinIcons.TRASH);
        del.addClickListener(e->{
            if(isBookBorrowed(book)){
                Notification.show("Book cannot be deleted while it is rented");
            }else{
                AppEventBus.post(new AppEvent.EditObjectEvent());
                masterController.getBookController().delete(book);
            }
        });
        return del;
    }

    private boolean isBookBorrowed(Book b) {
        for(Rent r : masterController.getRentController().getItems()){
            if(r.getBookId().intValue() == b.getId().intValue()){
                return true;
            }
        }
        return false;
    }

    @Override
    public ListDataProvider<Book> getDataProvider() {
        return dataProvider;
    }

    //Subscribe to events from other classes, to refresh the grid
    @Subscribe
    private void refreshGrid(final AppEvent.EditObjectEvent e){
        dataProvider = new ListDataProvider<>(masterController.getBookController().getItems());
        setDataProvider(dataProvider);
    }

    @Subscribe
    private void searchFilter(final AppEvent.SearchFilterEvent e){
        dataProvider.setFilter(book -> book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
                book.getTitle().toLowerCase().contains(e.getValue().toLowerCase()));
    }

}
