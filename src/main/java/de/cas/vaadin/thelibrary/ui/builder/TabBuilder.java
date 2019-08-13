package de.cas.vaadin.thelibrary.ui.builder;

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

    public TabBuilder(){
        //Tab for different stages
        tab = new TabSheet();
        tab.setSizeFull();
        tab.setResponsive(true);


        //Book tab
        books = new BookTab();
        tab.addTab(books).setIcon(VaadinIcons.BOOK);


        //Reader tab
        readers = new VerticalLayout();
        readers.addComponent(new ReaderTab());
        tab.addTab(readers).setIcon(VaadinIcons.USERS);
        tab.getTab(readers).setEnabled(false);

        //Deadline tab
        deadline = new HorizontalLayout();
        deadline.addComponent(new DeadlineTab());
        tab.addTab(deadline).setIcon(VaadinIcons.CALENDAR_CLOCK);
        tab.getTab(deadline).setEnabled(false);

        tab.addSelectedTabChangeListener(e->{
            updateLayouts();
        });

    }

    private void updateLayouts() {
        readers.removeAllComponents();
        readers.addComponent(new ReaderTab());

        deadline.removeAllComponents();
        deadline.addComponent(new DeadlineTab());
    }

    public static TabSheet getTabSheet() {
        return tab;
    }

}
