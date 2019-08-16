package de.cas.vaadin.thelibrary.controller;

import de.cas.vaadin.thelibrary.CASTheLibraryApplication;

public class MasterController {


    public static BookController getBookController() {
        return CASTheLibraryApplication.getInjector().getInstance(BookController.class);
    }

    public static ReaderController getReaderController() {
        return CASTheLibraryApplication.getInjector().getInstance(ReaderController.class);
    }

    public static RentController getRentController() {
        return CASTheLibraryApplication.getInjector().getInstance(RentController.class);
    }

    public static WaitlistController getWaitlistController() {
        return CASTheLibraryApplication.getInjector().getInstance(WaitlistController.class);
    }
}
