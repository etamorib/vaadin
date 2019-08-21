package de.cas.vaadin.thelibrary.ui.view.content;

import java.time.LocalDate;
import java.util.ArrayList;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.CASTheLibraryApplication;
import de.cas.vaadin.thelibrary.controller.*;
import de.cas.vaadin.thelibrary.ui.builder.TabBuilder;
import org.vaadin.alump.fancylayouts.FancyCssLayout;

import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent.ChangeViewEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;
import de.cas.vaadin.thelibrary.ui.view.CreateContent;

/**This is the view of new rentals.
 * It consists of 3 stages: Book selection, reader selection and borrowing / adding to waitlist
 * @author mate.biro
 *
 */
public class NewRental implements CreateContent {

	private final String name = "New Rentals";
	private TabBuilder tabBuilder;
	@Inject
	public NewRental(TabBuilder tabBuilder) {
		this.tabBuilder = tabBuilder;
	}

	@Override
	public Component buildContent() {
		Label title = new Label("Add new rentals");
		title.setStyleName(ValoTheme.LABEL_H1);
		
		//The main layout
		VerticalLayout mainLayout = new VerticalLayout(title);

		mainLayout.addComponents(tabBuilder.getTabSheet());

		return mainLayout;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Resource menuIcon() {
		return VaadinIcons.PLUS;
	}
}
