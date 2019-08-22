package de.cas.vaadin.thelibrary.ui.view.content.readerview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Resource;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class ReaderView extends HorizontalLayout implements CreateContent {

    private final String name ="Readers";

    private Provider<ReaderContent> readerContentProvider;
    private ReaderContent readerContent;

    @Inject
    public ReaderView(Provider<ReaderContent> readerContentProvider){
        this.readerContentProvider = readerContentProvider;
    }

    @Override
    public Component buildContent() {
        readerContent = readerContentProvider.get();
        removeAllComponents();
        addComponent(readerContent);
        setSizeFull();
        return this;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        buildContent();
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Resource menuIcon() {
        return VaadinIcons.USERS;
    }

}
