package de.cas.vaadin.thelibrary.ui.view.content;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;
/**
 * Test class
 */
public class NewBookView implements CreateContent {

    private HorizontalLayout mainLayout;

    @Override
    public Component buildContent() {
        mainLayout = new HorizontalLayout();

        mainLayout.addComponents(buildBody());
        return mainLayout;
    }

    private Component buildBody(){
        VerticalLayout layout = new VerticalLayout();
        for(Book b: MasterController.getBookController().getItems()){
            layout.addComponent(createBookForm(b));
        }
        return layout;
    }

    private Component createBookForm(Book book) {
        return null;
    }

    @Override
    public String getName() {
        return "NewBookView";
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.BOOK;
    }
}
