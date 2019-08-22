package de.cas.vaadin.thelibrary.ui.view.content.readerview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.ui.view.content.bookview.BookAddWindow;

public class ReaderMenu extends HorizontalLayout {
    private Provider<Button> buttonProvider;
    private Provider<TextField> textFieldProvider;
    private Button add;
    private ReaderAddWindow readerAddWindow;

    @Inject
    public ReaderMenu(Provider<Button> buttonProvider,
                    Provider<TextField> textFieldProvider,
                      ReaderAddWindow readerAddWindow)
    {
        this.buttonProvider = buttonProvider;
        this.textFieldProvider = textFieldProvider;
        this.readerAddWindow = readerAddWindow;
        buildMenu();
    }

    private void buildMenu(){
        //Add Button
        add = buttonProvider.get();
        add.setIcon(VaadinIcons.PLUS);
        add.setStyleName("header-button");
        add.setDescription("Add new reader to database");
        setAddClicklistener();

        TextField search  = textFieldProvider.get();
        search.setIcon(VaadinIcons.SEARCH);
        search.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);

        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.EAGER);
        search.addValueChangeListener(e->{
            AppEventBus.post(new AppEvent.SearchFilterEvent(e.getValue()));
        });
        addComponents(add, search);
    }

    private void setAddClicklistener() {
        add.addClickListener(e->{
            AppEventBus.post(new AppEvent.CloseOpenedWindowsEvent());
            readerAddWindow.createWindow();
        });
    }
}
