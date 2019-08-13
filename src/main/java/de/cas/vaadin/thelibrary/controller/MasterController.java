package de.cas.vaadin.thelibrary.controller;

public class MasterController {
    private static final BookController bookController = new BookController();
    private static final ReaderController readerController = new ReaderController();
    private static final RentController rentController = new RentController();
    private static final WaitlistController waitlistController = new WaitlistController();

    public static BookController getBookController() {
        return bookController;
    }

    public static ReaderController getReaderController() {
        return readerController;
    }

    public static RentController getRentController() {
        return rentController;
    }

    public static WaitlistController getWaitlistController() {
        return waitlistController;
    }
}
