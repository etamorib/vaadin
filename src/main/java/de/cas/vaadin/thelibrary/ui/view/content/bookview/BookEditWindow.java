package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Category;
import org.vaadin.ui.NumberField;

import java.time.Year;
import java.util.ArrayList;

public class BookEditWindow extends Window {

    private Book book;
    private Provider<NativeSelect<Category>> categoryProvider;
    private Provider<Button> buttonProvider;
    private Provider<NativeSelect<Integer>> yearProvider;
    private Provider<NativeSelect<BookState>> stateProvider;
    private Provider<TextField> textFieldProvider;
    private Provider<NumberField> numberFieldProvider;
    private MasterController masterController;

    @Inject
    public BookEditWindow(Provider<NativeSelect<Category>> categoryProvider,
                          Provider<Button> buttonProvider,
                          Provider<NativeSelect<Integer>> yearProvider,
                          Provider<NativeSelect<BookState>> stateProvider,
                          Provider<TextField> textFieldProvider,
                          Provider<NumberField> numberFieldProvider,
                          MasterController masterController)
    {
        this.categoryProvider = categoryProvider;
        this.buttonProvider = buttonProvider;
        this.yearProvider = yearProvider;
        this.stateProvider = stateProvider;
        this.textFieldProvider = textFieldProvider;
        this.numberFieldProvider = numberFieldProvider;
        this.masterController = masterController;
    }
    @Inject
    public void setBook(Book book){
        this.book = book;
    }

    void createWindow(){
        setCaption(book.getTitle());
        addStyleName("add-window");
//        setSizeUndefined();
        setHeight(65f, Unit.PERCENTAGE);
        setWidth(20f, Unit.PERCENTAGE);
        setResizable(false);
        center();
        setDraggable(false);
        setModal(true);


        final FormLayout editForm = new FormLayout();

        //Buttons
        //Save
        Button save = buttonProvider.get();
        save.setCaption("Save");
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);

        //Cancel
        Button cancel = buttonProvider.get();
        cancel.setCaption("Cancel");
        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);

        //Form

        TextField title = textFieldProvider.get();
        title.setCaption("Title");
        title.setValue(book.getTitle());
        TextField author = textFieldProvider.get();
        author.setCaption("Author");
        author.setValue(book.getAuthor());

        //Select the state
        NativeSelect<BookState> state = stateProvider.get();
        state.setCaption("State");
        state.setValue(book.getState());
        state.setStyleName("dropdown-select");
        state.setEmptySelectionAllowed(false);

        //Select the year
        NativeSelect<Integer> selectYear = yearProvider.get();
        selectYear.setCaption("Year");
        selectYear.setValue(book.getYear());
        selectYear.setStyleName("dropdown-select");

        //If book is borrowed, state cannot be changed
        if(book.getState()==BookState.Borrowed) {
            state.setEnabled(false);
        }
        //Setting the values of the selector
        state.setItems(BookState.Available, BookState.Deleted);
        selectYear.setItems(setYearsIntervall(1600));

        //If a book can have more category i'll need to change this
        NativeSelect<Category> category = categoryProvider.get();
        category.setCaption("Category");
        category.setStyleName("dropdown-select");
        category.setValue(book.getCategory());
        category.setItems(Category.class.getEnumConstants());

        NumberField number = numberFieldProvider.get();
        number.setCaption("Available");
        number.setValue(book.getNumber().toString());

        NumberField id = numberFieldProvider.get();
        id.setCaption("id");
        id.setEnabled(false);
        id.setValue(book.getId().toString());

        //Save button clicklistener
        save.addClickListener(e->{
            Book b = new Book(title.getValue(), author.getValue(),
                    Integer.parseInt(id.getValue()), selectYear.getValue(), state.getValue(),
                    category.getValue(), Integer.parseInt(number.getValue()));
            addSaveClickListener(b);
            this.close();
        });

        //Cancel
        cancel.addClickListener(e->{
           this.close();
        });

        editForm.setSizeFull();
        HorizontalLayout bottom = new HorizontalLayout(save, cancel);
        editForm.addComponents(title, author, category,state, selectYear, number,id, bottom);
        setContent(editForm);
        UI.getCurrent().addWindow(this);
    }

    private ArrayList<Integer> setYearsIntervall(int startYear) {
        ArrayList<Integer> years = new ArrayList<Integer>();
        for(int i = startYear; i<= Year.now().getValue(); i++) {
            years.add(i);
        }
        return years;
    }

    private void addSaveClickListener(Book book) {
        if(masterController.getBookController().update(book)) {
            AppEventBus.post(new AppEvent.EditObjectEvent());
            Notification.show("Update successful");
        }
        else {
            Notification.show("Something went wrong!");
        }
    }
}
