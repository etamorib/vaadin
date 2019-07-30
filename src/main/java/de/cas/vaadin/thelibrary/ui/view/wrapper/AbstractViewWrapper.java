package de.cas.vaadin.thelibrary.ui.view.wrapper;

import com.vaadin.navigator.View;
import com.vaadin.ui.Composite;
import com.vaadin.ui.Label;
//TODO: rename
public abstract class AbstractViewWrapper extends Composite implements View {
	public abstract String name();
	public AbstractViewWrapper() {
		setCompositionRoot(new Label("ASDASD"));
	}
}
