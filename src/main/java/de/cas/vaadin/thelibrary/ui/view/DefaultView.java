package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

public class DefaultView extends Composite implements View {

	public static String name = "";
	public DefaultView() {
		setCompositionRoot(new Label("+++"));
	}

	@Override
	public String toString() {
		return name;
	}
	
}
