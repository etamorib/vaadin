package de.cas.vaadin.thelibrary.ui.view.content.waitlistview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

public class WaitListView extends HorizontalLayout implements CreateContent {

    private final String name ="Waitlist";
    private Provider<WaitListContent> waitListContentProvider;
    private WaitListContent waitListContent;

    @Inject
    public WaitListView(Provider<WaitListContent> waitListContentProvider){

        this.waitListContentProvider = waitListContentProvider;
        buildContent();
    }

    @Override
    public void buildContent() {
        waitListContent = waitListContentProvider.get();
        removeAllComponents();
        addComponent(waitListContent);
        setSizeFull();
    }

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Resource menuIcon() {
		return VaadinIcons.TIMER;
	}
}
