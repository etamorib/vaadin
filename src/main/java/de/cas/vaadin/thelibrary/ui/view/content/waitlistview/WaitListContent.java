package de.cas.vaadin.thelibrary.ui.view.content.waitlistview;

import com.google.inject.Inject;
import com.vaadin.ui.VerticalLayout;

public class WaitListContent extends VerticalLayout {

    private WaitListGrid waitListGrid;

    @Inject
    public WaitListContent(WaitListGrid waitListGrid) {
        this.waitListGrid = waitListGrid;
        setCaptionAsHtml(true);
        setCaption("Waitlisted requests");
        buildLayout();
    }

    private void buildLayout() {
        addComponents(waitListGrid);
    }
}
