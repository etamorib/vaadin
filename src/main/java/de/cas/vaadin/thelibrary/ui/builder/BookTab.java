package de.cas.vaadin.thelibrary.ui.builder;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.BookController;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;

import java.util.Set;

public class BookTab extends VerticalLayout {
    private BookController bookController = new BookController();
    private static Grid<Book> bookGrid;
    private ListDataProvider<Book> bookDataProvider;
    private int maxSelect = 5;
    public static ListSelect<Book> list = null;
    public static Set<Book> selectedBooks = null;


    public BookTab(){
       buildTabLayout();
    }

    private void updateCounter(Label l, int i) {
        l.setCaption(i+" items are selected, "+ (maxSelect-i)+" remaining.");
    }

    private void buildTabLayout(){
        Label title = new Label("Select books that want to be rented ");
        title.setStyleName(ValoTheme.LABEL_H2);

        //Counter for selected items
        Label counter = new Label();

        //Button for adding
        Button add = new Button("Select books");
        add.setStyleName("header-button");
        add.setSizeFull();
        add.setEnabled(false);

        //Grid
        bookGrid = new Grid<>(Book.class);
        bookDataProvider = new ListDataProvider<>(bookController.getItems());
        //To make sure column order
        bookGrid.setColumns("author","title", "id", "year", "state");
        bookGrid.setSelectionMode(Grid.SelectionMode.MULTI);
        bookGrid.setStyleName("grid-overall");
        bookGrid.setDataProvider(bookDataProvider);
        bookGrid.setSizeFull();
        //Filter
        bookDataProvider.setFilter(book->book.getState() == BookState.Available || book.getState()==BookState.Borrowed);
        NativeSelect<BookState> state = new NativeSelect<>();
        state.setStyleName("dropdown-select");
        state.setItems(BookState.Available, BookState.Borrowed);
        //Filter for state
        state.addSelectionListener(e->{
            if(e.getValue()!=null) {
                bookDataProvider.setFilter(book -> book.getState() ==e.getValue());
            }else {
                bookDataProvider.setFilter(book->book.getState() == BookState.Available || book.getState()==BookState.Borrowed);

            }

        });

        //Grid selection listener, to prevent more than 5 selects
        bookGrid.addSelectionListener(e->{
            selectedBooks = bookGrid.getSelectedItems();
            int currentlySelected = bookGrid.getSelectedItems().size();

            updateCounter(counter, currentlySelected);
            //If there are more than 5 selections, it deselects the first, then second ...
            if(currentlySelected> maxSelect) {
                bookGrid.deselect(bookGrid.getSelectedItems().iterator().next());
            }
            if(currentlySelected > 0) {
                //Adding selected items to the ListSelect to show on next tab
                list = new ListSelect<>();
                list.addStyleName("white-text");
                list.setItems(bookGrid.getSelectedItems());
                list.setSizeFull();

                //Go to next tab
                add.setEnabled(true);
                add.addClickListener(event->{
                    TabBuilder.getTabSheet().getTab(1).setEnabled(true);
                    TabBuilder.getTabSheet().setSelectedTab(1);

                });
            }
            //If books are deselected further tabs are disabled
            else {
                TabBuilder.getTabSheet().getTab(1).setEnabled(false);
                add.setEnabled(false);
            }
        });


        bookGrid.addHeaderRowAt(1);
        bookGrid.getHeaderRow(1).getCell("state").setComponent(state);

        //Search field
        TextField search  = new TextField();
        search.setIcon(VaadinIcons.SEARCH);
        search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e->{
            //Filter for both title and author
            bookDataProvider.setFilter(book -> (book.getAuthor().toLowerCase().contains(e.getValue().toLowerCase())||
                    book.getTitle().toLowerCase().contains(e.getValue().toLowerCase())) &&
                    book.getState()!=BookState.Deleted);

        });

        addComponents(title, search, bookGrid, counter, add);
    }

    public static Grid<Book> getBookGrid() {
        return bookGrid;
    }
}
