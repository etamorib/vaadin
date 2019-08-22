package de.cas.vaadin.thelibrary.ui.view.content.readerview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import org.vaadin.ui.NumberField;

public class ReaderAddWindow extends Window {

    private MasterController masterController;
    private Provider<TextField> textFieldProvider;
    private Provider<NumberField> numberFieldProvider;
    private Provider<Button> buttonProvider;

    @Inject
    public ReaderAddWindow(MasterController masterController,
                         Provider<TextField> textFieldProvider,
                         Provider<NumberField> numberFieldProvider,
                         Provider<Button> buttonProvider)
    {
        this.masterController = masterController;
        this.textFieldProvider = textFieldProvider;
        this.numberFieldProvider = numberFieldProvider;
        this.buttonProvider = buttonProvider;
    }

    void createWindow(){
        setCaption("Add new reader");
        FormLayout form = new FormLayout();
        form.setStyleName("black-text");

        TextField name = textFieldProvider.get();
        name.setCaption("Name");

        name.setRequiredIndicatorVisible(true);

        TextField address = textFieldProvider.get();
        address.setCaption("Address");
        address.setIcon(VaadinIcons.HOME);
        address.setRequiredIndicatorVisible(true);

        TextField email = textFieldProvider.get();
        email.setCaption("Email");
        email.setIcon(VaadinIcons.AT);
        email.setRequiredIndicatorVisible(true);



        NumberField phone = numberFieldProvider.get();
        phone.setCaption("Phone");

        phone.setIcon(VaadinIcons.PHONE);
        phone.setRequiredIndicatorVisible(true);
        phone.setNegativeAllowed(false);
        phone.setGroupingUsed(false);



        //Add button
        Button add = buttonProvider.get();
        add.setCaption("Add");
        add.setStyleName(ValoTheme.BUTTON_PRIMARY);
        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        add.setIcon(VaadinIcons.PLUS);
        add.addClickListener(e->{
            Reader r = new Reader(name.getValue(),
                    address.getValue(),
                    email.getValue(),
                   Long.parseLong(phone.getValue()));
            if(masterController.getReaderController().add(r)) {
                close();
                Notification.show("Reader has been added to database");
                AppEventBus.post(new AppEvent.EditObjectEvent());
            }
        });

        form.addComponents(name, address, email, phone, add);
        form.setSizeUndefined();

        setContent(form);
        addStyleName("add-window");
        setSizeUndefined();
        setResizable(false);
        center();
        setDraggable(false);
        setModal(true);

        UI.getCurrent().addWindow(this);
    }
}
