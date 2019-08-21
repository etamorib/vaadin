package de.cas.vaadin.thelibrary.ui.view.loginview;


import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

public class LabelBuilder extends CssLayout {

    private Label welcome, title;
    private Provider<Label> labelProvider;

    @Inject
    public LabelBuilder(Provider<Label> labelProvider){
        this.labelProvider = labelProvider;
        welcome = this.labelProvider.get();
        title = this.labelProvider.get();
        buildLabels();
    }

    private void buildLabels() {
        //Returned layout

        addStyleName("labels");

        //Welcome label
        welcome.setValue("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName(ValoTheme.LABEL_H3);
        welcome.addStyleName(ValoTheme.LABEL_COLORED);

        //Title label
        title.setValue("Vaadin Library App");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H2);
        title.addStyleName(ValoTheme.LABEL_LIGHT);
        title.addStyleName("text-color-white");
        addComponents(welcome, title);

    }
}
