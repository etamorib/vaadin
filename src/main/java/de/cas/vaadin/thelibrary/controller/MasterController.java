package de.cas.vaadin.thelibrary.controller;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.CASTheLibraryApplication;

public class MasterController {

    private BookController bookController;
    private RentController rentController;
    private ReaderController readerController;
    private WaitlistController waitlistController;

    @Inject
    public MasterController(BookController bookController, RentController rentController, ReaderController readerController,
                            WaitlistController waitlistController){
        this.bookController = bookController;
        this.readerController = readerController;
        this.rentController = rentController;
        this.waitlistController = waitlistController;
    }

    public BookController getBookController() {
        return this.bookController;
    }

    public  ReaderController getReaderController() {
        return this.readerController;
    }

    public RentController getRentController() {
        return this.rentController;
    }

    public WaitlistController getWaitlistController() {
        return this.waitlistController;
    }
}
