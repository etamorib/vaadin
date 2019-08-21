package de.cas.vaadin.thelibrary.ui.view.content.bookview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Category;
import org.vaadin.ui.NumberField;

public class BookAddWindow extends Window {

    private MasterController masterController;
    private Provider<TextField> textFieldProvider;
    private Provider<NumberField> numberFieldProvider;
    private Provider<NativeSelect<Category>> categorySelectProvider;
    private Provider<Button> buttonProvider;

    @Inject
    public BookAddWindow(MasterController masterController,
                         Provider<TextField> textFieldProvider,
                         Provider<NumberField> numberFieldProvider,
                         Provider<NativeSelect<Category>> categorySelectProvider,
                         Provider<Button> buttonProvider)
    {
        this.masterController = masterController;
        this.textFieldProvider = textFieldProvider;
        this.numberFieldProvider = numberFieldProvider;
        this.categorySelectProvider = categorySelectProvider;
        this.buttonProvider = buttonProvider;
    }

    void createWindow(){
        setCaption("Add new book");
        FormLayout form = new FormLayout();
        form.setStyleName("black-text");
        //author
        TextField author = textFieldProvider.get();
        author.setCaption("Author");
        author.setIcon(VaadinIcons.PENCIL);
        author.setRequiredIndicatorVisible(true);
        //title
        TextField title = textFieldProvider.get();
        title.setCaption("Title");
        title.setIcon(VaadinIcons.TEXT_LABEL);
        title.setRequiredIndicatorVisible(true);
        //ID
        NumberField id = numberFieldProvider.get();
        id.setCaption("Id");
        id.setStyleName("dropdown-select");
        id.setIcon(VaadinIcons.EXCLAMATION);
        id.setRequiredIndicatorVisible(true);
        id.setNegativeAllowed(false);
        id.setGroupingUsed(false);
        //Year
        NumberField year = numberFieldProvider.get();
        year.setCaption("Year");

        year.setIcon(VaadinIcons.CALENDAR);
        year.setRequiredIndicatorVisible(true);
        year.setNegativeAllowed(false);
        year.setGroupingUsed(false);

        NativeSelect<Category> category = categorySelectProvider.get();
        category.setCaption("Category");
        category.setItems(Category.class.getEnumConstants());
        category.setStyleName("dropdown-select");

        NumberField number = numberFieldProvider.get();
        number.setCaption("Available number");

        //Add button
        Button add = buttonProvider.get();
        add.setCaption("Add");
        add.setStyleName(ValoTheme.BUTTON_PRIMARY);
        add.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        add.setIcon(VaadinIcons.PLUS);
        add.addClickListener(e->{
            Book b = new Book(title.getValue(), author.getValue(), Integer.parseInt(id.getValue()),
                    Integer.parseInt(year.getValue()), BookState.Available, category.getValue(), Integer.parseInt(number.getValue()));
            if(masterController.getBookController().add(b)) {
                close();
                Notification.show("Book has been added to database");

                AppEventBus.post(new AppEvent.EditObjectEvent());

            }
        });

        form.addComponents(id,author, title, category, year, number,add);
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
