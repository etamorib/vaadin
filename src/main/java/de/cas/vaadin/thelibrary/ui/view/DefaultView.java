package de.cas.vaadin.thelibrary.ui.view;


import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;

import de.cas.vaadin.thelibrary.ui.view.wrapper.AbstractViewWrapper;

public class DefaultView extends AbstractViewWrapper implements View {

	public final String name = "";
	

	@Override
	public String name() {
		return name;
	}
	
}
