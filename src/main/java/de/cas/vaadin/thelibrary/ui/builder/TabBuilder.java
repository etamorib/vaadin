package de.cas.vaadin.thelibrary.ui.builder;

import com.google.inject.Inject;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class TabBuilder {

    private static TabSheet tab;
    //TABS:
    private VerticalLayout books, readers;
    private HorizontalLayout deadline;

    @Inject
    public TabBuilder(BookTab books, ReaderTab readers, DeadlineTab deadline){
        //Tab for different stages
        tab = new TabSheet();
        tab.setSizeFull();
        tab.setResponsive(true);


        //Book tab
        this.books = books;
        tab.addTab(this.books).setIcon(VaadinIcons.BOOK);


        //Reader tab
        this.readers = readers;
        this.readers.addComponent(readers);
        tab.addTab(this.readers).setIcon(VaadinIcons.USERS);
        tab.getTab(this.readers).setEnabled(false);

        //Deadline tab
        this.deadline = deadline;
        this.deadline.addComponent(deadline);
        tab.addTab(this.deadline).setIcon(VaadinIcons.CALENDAR_CLOCK);
        tab.getTab(this.deadline).setEnabled(false);

        tab.addSelectedTabChangeListener(e->{
            updateLayouts();
        });

    }

    private void updateLayouts() {
        readers.removeAllComponents();
        readers.addComponent(readers);

        deadline.removeAllComponents();
        deadline.addComponent(deadline);
    }

    public static TabSheet getTabSheet() {
        return tab;
    }

}
