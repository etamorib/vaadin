package de.cas.vaadin.thelibrary.ui.view.content.readerview;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Category;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import org.vaadin.ui.NumberField;

import java.time.Year;
import java.util.ArrayList;

public class ReaderEditWindow extends Window {

    private Reader reader;

    private Provider<Button> buttonProvider;
    private Provider<TextField> textFieldProvider;
    private Provider<NumberField> numberFieldProvider;
    private MasterController masterController;

    @Inject
    public ReaderEditWindow(Provider<Button> buttonProvider,
                          Provider<TextField> textFieldProvider,
                          Provider<NumberField> numberFieldProvider,
                          MasterController masterController)
    {
        this.buttonProvider = buttonProvider;
        this.textFieldProvider = textFieldProvider;
        this.numberFieldProvider = numberFieldProvider;
        this.masterController = masterController;
    }
    @Inject
    public void setReader(Reader reader){
        this.reader = reader;
    }

    void createWindow(){
        setCaption(reader.getName());
        addStyleName("add-window");
//        setSizeUndefined();
        setHeight(65f, Unit.PERCENTAGE);
        setWidth(20f, Unit.PERCENTAGE);
        setResizable(false);
        center();
        setDraggable(false);
        setModal(true);


        final FormLayout editForm = new FormLayout();

        //Buttons
        //Save
        Button save = buttonProvider.get();
        save.setCaption("Save");
        save.setStyleName(ValoTheme.BUTTON_PRIMARY);

        //Cancel
        Button cancel = buttonProvider.get();
        cancel.setCaption("Cancel");
        cancel.setStyleName(ValoTheme.BUTTON_PRIMARY);

        //Form

        TextField name = textFieldProvider.get();
        name.setCaption("Name");
        name.setValue(reader.getName());
        TextField address = textFieldProvider.get();
        address.setCaption("Address");
        address.setValue(reader.getAddress());

        TextField email = textFieldProvider.get();
        email.setCaption("Address");
        email.setValue(reader.getEmail());
        //Select the state


        NumberField phone = numberFieldProvider.get();
        phone.setCaption("Available");
        phone.setValue(reader.getPhoneNumber().toString());

        NumberField id = numberFieldProvider.get();
        id.setCaption("id");
        id.setEnabled(false);
        id.setValue(reader.getId().toString());

        //Save button clicklistener
        save.addClickListener(e->{
            Reader r = new Reader(name.getValue(), address.getValue(), email.getValue(), Integer.parseInt(id.getValue()),Long.parseLong(phone.getValue()));
            addSaveClickListener(r);
            this.close();
        });

        //Cancel
        cancel.addClickListener(e->{
            this.close();
        });

        editForm.setSizeFull();
        HorizontalLayout bottom = new HorizontalLayout(save, cancel);
        editForm.addComponents(name, address, email,phone,  id, bottom);
        setContent(editForm);
        UI.getCurrent().addWindow(this);
    }


    private void addSaveClickListener(Reader reader) {
        if(masterController.getReaderController().update(reader)) {
            AppEventBus.post(new AppEvent.EditObjectEvent());
            Notification.show("Update successful");
        }
        else {
            Notification.show("Something went wrong!");
        }
    }
}
