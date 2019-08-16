package de.cas.vaadin.thelibrary.ui.builder;

import com.google.inject.Inject;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.CASTheLibraryApplication;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.*;
import de.cas.vaadin.thelibrary.ui.view.content.NewRental;
import org.vaadin.alump.fancylayouts.FancyCssLayout;

import java.time.LocalDate;
import java.util.ArrayList;

class DeadlineTab extends HorizontalLayout {
    private FancyCssLayout left ;
    private ListDataProvider<Book> borrowedBookProvider;
    private ArrayList<Book> availableList;
    private  ArrayList<Book> borrowedList;
    private Grid<Book> availableGrid, borrowedGrid;
    private Label ava, borr;
    private MasterController masterController;

    @Inject
    public DeadlineTab(MasterController masterController){
        this.masterController = masterController;
        buildTabLayout();
    }

    private void buildTabLayout(){
        System.out.println("DEADLINE: "+ ReaderTab.selectedReader);
        left = new FancyCssLayout();
        left.setSizeFull();
        setSizeFull();
        availableGrid = new Grid<>(Book.class);
        borrowedGrid = new Grid<>(Book.class);
        availableGrid.setStyleName("grid-overall");
        borrowedGrid.setStyleName("grid-overall");

        //Labels for grid titles:
        ava = new Label("Currently available books - rent");
        borr = new Label("Currently borrowed books - to waitlist");

        //Set containers for available books and borrowed books
        fillLists();
        //Grid for available books
        availableGrid.setItems(availableList);
        availableGrid.setSizeFull();


        //Grid for borrowed books
        borrowedBookProvider = new ListDataProvider<>(borrowedList);
        borrowedGrid.setDataProvider(borrowedBookProvider);
        borrowedGrid.setSizeFull();


        if(availableList.size()>0) {
            buildAvailableForm();
        }
        //If there was any borrowed books selected
        if(borrowedList.size()>0) {
            buildBorrowedForm();
        }
        //Calls the current reader form and with the right boolean parameter
        //to show deadline date
        addComponents(left, ReaderTab.selectedReader!=null?availableList.size()>0? new VerticalLayout(currentReaderForm(ReaderTab.selectedReader, true)):
                new VerticalLayout(currentReaderForm(ReaderTab.selectedReader, false)):new Label("no user"));

    }
    //Build a form for the reader who wants to rent
    private ComponentContainer currentReaderForm(Reader reader, boolean available) {
        VerticalLayout layout = new VerticalLayout();
        layout.setCaption("Renter");

        //All the neccessary labels
        Label name, address, email, tel, id, deadline;
        name = new Label("Name: "+reader.getName());
        address = new Label("Address: "+reader.getAddress());
        email = new Label("Email: "+reader.getEmail());
        tel = new Label("Phone number: "+reader.getPhoneNumber().toString());
        id = new Label("ID: "+reader.getId().toString());
        deadline = new Label("Books can be rented until: "+ LocalDate.now().plusMonths(2).toString());

        deadline.setStyleName(ValoTheme.LABEL_BOLD);
        layout.addComponents(name, address, email, tel, id);

        //Available tells if there were any available books selected
        //If so, a deadline date is also shown
        if(available) {
            layout.addComponent(deadline);
        }
        return layout;
    }

    private void waitlistWindow(ArrayList<Book> borrowedBooks, Label borr, Grid<Book> borrowedGrid, Button wl) {
        Window window = new Window();
        window.setCaption("Set date");
        HorizontalLayout layout = new HorizontalLayout();

        for(Book b: borrowedBooks) {
            //Form for the book's data
            FormLayout form = new FormLayout();
            //Author
            TextField author = new TextField("Author");
            author.setIcon(VaadinIcons.PENCIL);
            author.setValue(b.getAuthor());
            author.setEnabled(false);

            //title
            TextField title = new TextField("Title");
            title.setIcon(VaadinIcons.TEXT_LABEL);
            title.setValue(b.getTitle());
            title.setEnabled(false);


            TextField year = new TextField("Year");
            year.setIcon(VaadinIcons.CALENDAR);
            year.setValue(b.getYear().toString());
            year.setEnabled(false);

            TextField rentedUntil = new TextField("Rented until");
            year.setIcon(VaadinIcons.CALENDAR_USER);
            //Sets the time when the book will be returned
            rentedUntil.setValue(masterController.getRentController().getRentByBookId(b.getId()).getReturnTime().toString());
            rentedUntil.setEnabled(false);
            DateField dateField = new DateField();
            //DateFields starts from the return date
            dateField.setValue(masterController.getRentController().getRentByBookId(b.getId()).getReturnTime());
            Button add = new Button("Set date");
            add.addClickListener(e->{
                //If adding to waitlist database is not successful
                if(!masterController.getWaitlistController().add(new Waitlist(b.getId(),
                        ReaderTab.selectedReader.getId(), LocalDate.now(), dateField.getValue()))) {
                    Notification.show("Something went wrong!");
                }
                //If it was successful
                else {
                    Notification.show("Request added to waitlist");
                }

                //Logic of removing components based on their number
                //and refreshing the view
                layout.removeComponent(form);
                borrowedBooks.remove(b);
                borrowedBookProvider = new ListDataProvider<>(borrowedBooks);
                borrowedGrid.setDataProvider(borrowedBookProvider);
                if(layout.getComponentCount()==0) {
                    left.fancyRemoveComponent(borr);
                    left.fancyRemoveComponent(borrowedGrid);
                    left.fancyRemoveComponent(wl);
                    window.close();
                    Notification.show("Books have been added to waitlist");

                    if(left.getComponentCount()==3) {
                        System.out.println("BACK TO NEW RENTAL");
                        AppEventBus.post(new AppEvent.ChangeViewEvent(CASTheLibraryApplication.getInjector().getInstance(NewRental.class)));
                    }
                }

            });

            form.addComponents(author, title, year, rentedUntil, dateField ,add);
            layout.addComponent(form);
        }
        window.setContent(layout);
        window.addStyleName("add-window");
        window.setResizable(false);
        window.center();
        window.setDraggable(false);
        window.setModal(true);
        //Show the window
        UI.getCurrent().addWindow(window);

    }

    private void fillLists(){
        availableList = new ArrayList<Book>();
        borrowedList = new ArrayList<Book>();
        for(Book b: BookTab.getBookGrid().getSelectedItems()) {
            if(b.getState() == BookState.Available) {
                availableList.add(b);
            }
            if(b.getState() == BookState.Borrowed) {
                borrowedList.add(b);
            }
        }

    }

    private void buildAvailableForm(){
        Button rent = new Button("Borrow");
        rent.addStyleName("header-button");
        rent.setSizeFull();
        rent.addClickListener(e->{
            //Adds rent to database and removes components from layout
            boolean error = false;
            left.fancyRemoveComponent(ava);
            left.fancyRemoveComponent(availableGrid);
            left.fancyRemoveComponent(rent);
            for(Book b: availableList) {
                //Checks if there is error while adding to rent database
                if(!masterController.getRentController().add(new Rent(LocalDate.now(), LocalDate.now().plusMonths(2), b.getId(), ReaderTab.selectedReader.getId()))) {
                    error = true;
                }
                //If there was no error it updates the database (sets book to borrowed)
                else {
                    if(b.getNumber()>1)
                        b.setNumber(b.getNumber()-1);
                    else {
                        b.setNumber(0);
                        b.setState(BookState.Borrowed);
                    }
                    masterController.getBookController().update(b);
                    //bookGrid.deselect(b);
                }

            }
            showError(error);
            //If no more components left, it sends back to the beginning of the view
            changeByComponentCount(3);
        });
        left.addComponents(ava, availableGrid, rent);
    }
    private void showError(boolean error){
        if(error) {
            Notification.show("ERROR");
        }
        else {
            Notification.show("SUCCESS");
        }
    }
    private void changeByComponentCount(int n){
        if(left.getComponentCount()==n) {
            AppEventBus.post(new AppEvent.ChangeViewEvent(CASTheLibraryApplication.getInjector().getInstance(NewRental.class)));
        }
    }

    private void buildBorrowedForm(){
        Button wl = new Button("Add to waitlist");
        wl.setStyleName("header-button");
        wl.setSizeFull();
        wl.addClickListener(e->{
            //Creates a popup window
            waitlistWindow(borrowedList, borr, borrowedGrid, wl);
        });
        left.addComponents(borr, borrowedGrid, wl);
    }
}
