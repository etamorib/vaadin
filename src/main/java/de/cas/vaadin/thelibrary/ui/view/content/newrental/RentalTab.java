package de.cas.vaadin.thelibrary.ui.view.content.newrental;

import com.google.inject.Inject;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;

public class RentalTab extends TabSheet {

    private BookTab bookTab;
    private ReaderTab readerTab;
    private DeadlineTab deadlineTab;

    @Inject
    public RentalTab(BookTab bookTab, ReaderTab readerTab, DeadlineTab deadlineTab){
        this.bookTab = bookTab;
        this.readerTab = readerTab;
        this.deadlineTab = deadlineTab;
        customizeTab();
    }

    private void customizeTab(){
        addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        setSizeFull();
        addComponent(bookTab);
        addComponent(readerTab);
        addComponent(deadlineTab);
        addSelectedTabChangeListener(e->{
            AppEventBus.post(new AppEvent.TabChangeEvent());
        });
    }
}
