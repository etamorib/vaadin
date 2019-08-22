package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.ui.*;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.*;
import org.vaadin.alump.fancylayouts.FancyCssLayout;

import java.time.LocalDate;
import java.util.ArrayList;

public class DeadlineTab extends VerticalLayout {

    private ArrayList<Book> available;
    private ArrayList<Book> borrowed;
    private Provider<Panel> panelProvider;
    private Provider<Label> labelProvider;
    private Provider<Button> buttonProvider;
    private Panel avail, borr;
    private HorizontalLayout books;
    private Label reader;
    private MasterController masterController;
    private Reader Reader = null;

    @Inject
    public DeadlineTab(ArrayList<Book> available,
                       ArrayList<Book> borrowed,
                       Provider<Panel> panelProvider,
                       Provider<Label> labelProvider,
                       Provider<Button> buttonProvider,
                       MasterController masterController)
    {
        this.buttonProvider = buttonProvider;
        this.masterController = masterController;
        AppEventBus.register(this);
        this.available = available;
        this.borrowed = borrowed;
        this.panelProvider = panelProvider;
        this.labelProvider = labelProvider;
        init();
        addComponents( reader,books);
    }

    private void init(){
        setCaption("Deadline");
        borr = panelProvider.get();
        avail = panelProvider.get();
        books = new HorizontalLayout();
        books.setSizeFull();
        reader = labelProvider.get();
        reader.setCaptionAsHtml(true);
        reader.setCaption("<h3>No reader is selected</h3>");
    }

    @Subscribe
    private void selectedBooks(final AppEvent.SelectedBooksEvent e){
        available.clear();
        borrowed.clear();
        for(Book b: e.getBooks()){
            if(b.getState()== BookState.Available){
                available.add(b);
            }
            if(b.getState() == BookState.Borrowed){
                borrowed.add(b);
            }
        }
        System.out.println(available);

    }

    private void build(){

        VerticalLayout left = new VerticalLayout();
        FancyCssLayout right = new FancyCssLayout();
        if(Reader==null){
           avail.setEnabled(false);
           borr.setEnabled(false);
        }
        Button add = buttonProvider.get();
        add.setSizeFull();
        add.setCaption("Rent books");
        add.addClickListener(e->{
            for(Book b: available) {
                //Checks if there is error while adding to rent database
                if(!masterController.getRentController().add(new Rent(LocalDate.now(), LocalDate.now().plusMonths(2), b.getId(),Reader.getId()))) {
                    Notification.show("Something went wrong");
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
                    Notification.show("Rent has been confirmed");
                }

            }
            books.removeComponent(avail);
        });
        if(available.size()>0) {
            for (Book b : available) {
                left.addComponent(new Label(b.toString()));
            }
            left.addComponent(add);
            avail.setCaption("Available");
            avail.setContent(left);
            books.addComponent(avail);
        }

        if(borrowed.size()>0) {
            for (Book b : borrowed) {
                HorizontalLayout line = new HorizontalLayout();
                Label book = labelProvider.get();
                book.setCaption(b.toString());
                Button borrow = buttonProvider.get();
                DateField dateField = new DateField();
                //DateFields starts from the return date
                dateField.setValue(masterController.getRentController().getRentByBookId(b.getId()).getReturnTime());
                borrow.setCaption("Set date");
                borrow.addClickListener(e->{
                    if(Reader!=null) {
                        if (!masterController.getWaitlistController().add(new Waitlist(b.getId(),
                                Reader.getId(), LocalDate.now(), dateField.getValue()))) {
                            Notification.show("Something went wrong!");
                        }
                        //If it was successful
                        else {
                            Notification.show("Request added to waitlist");
                            right.fancyRemoveComponent(line);
                        }
                    }
                    if(right.getComponentCount()==1){
                        books.removeComponent(borr);
                    }
                });

                line.addComponents(book,  dateField, borrow);
                right.addComponent(line);
            }

            borr.setCaption("Borrowed");
            borr.setContent(right);
            books.addComponent(borr);

        }
    }

    @Subscribe
    private void selectedReader(final AppEvent.SelectedReaderEvent e){
        Reader = e.getReader();
        borr.setEnabled(true);
        avail.setEnabled(true);
        reader.setCaption("Reader: "+ e.getReader().toString());
    }

    @Subscribe
    private void update(final AppEvent.TabChangeEvent e){
        build();
    }

}
