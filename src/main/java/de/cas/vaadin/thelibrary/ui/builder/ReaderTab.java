package de.cas.vaadin.thelibrary.ui.builder;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Reader;

public class ReaderTab extends VerticalLayout {
    private Grid<Reader> readerGrid;
    private ListDataProvider<Reader> readerDataProvider;
    public static Reader selectedReader = null;


    public ReaderTab(){
        buildTabLayout();
    }

    private void buildTabLayout(){
        if(BookTab.list !=null) {
            Label currentItems = new Label("Currently selected books:");
            currentItems.setStyleName(ValoTheme.LABEL_H2);
            addComponent(currentItems);
            addComponent(BookTab.list);
        }

        //Title
        Label title = new Label("Select the reader");
        title.setStyleName(ValoTheme.LABEL_H2);

        //Add reader button
        Button add = new Button("Select reader");
        add.setSizeFull();
        add.setStyleName("header-button");
        add.setEnabled(false);

        //Reader grid
        readerGrid = new Grid<>(Reader.class);
        readerDataProvider = new ListDataProvider<>(MasterController.getReaderController().getItems());
        readerGrid.setSelectionMode(Grid.SelectionMode.SINGLE);
        readerGrid.setStyleName("grid-overall single-select");
        readerGrid.setDataProvider(readerDataProvider);
        readerGrid.setSizeFull();


        //continue if reader is selected
        readerGrid.addSelectionListener(e->{
            if(readerGrid.getSelectedItems().size()>0) {

                selectedReader = readerGrid.getSelectedItems().iterator().next();
                System.out.println(selectedReader);
                add.setEnabled(true);
                add.addClickListener(event->{
                    TabBuilder.getTabSheet().getTab(2).setEnabled(true);
                    TabBuilder.getTabSheet().setSelectedTab(2);
                });
            }
            else {

                TabBuilder.getTabSheet().getTab(2).setEnabled(false);
                add.setEnabled(false);
            }
        });


        //Searchfield
        TextField search  = new TextField();
        search.setIcon(VaadinIcons.SEARCH);
        search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        search.setPlaceholder("Find a reader...");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e->{
            //Filter for both title or author or email or phone
            readerDataProvider.setFilter(reader-> reader.getName().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getAddress().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getEmail().toLowerCase().contains(e.getValue().toLowerCase())||
                    reader.getPhoneNumber().toString().toLowerCase().contains(e.getValue().toLowerCase()));

        });

        //TODO: maybe shortcut to register user here?

        addComponents(title,search ,readerGrid, add);
    }
}
