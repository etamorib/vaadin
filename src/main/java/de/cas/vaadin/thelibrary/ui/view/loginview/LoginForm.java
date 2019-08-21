package de.cas.vaadin.thelibrary.ui.view.loginview;

import com.google.inject.Inject;
import com.vaadin.server.Responsive;
import com.vaadin.ui.VerticalLayout;

public class LoginForm extends VerticalLayout {

    private LabelBuilder labelBuilder;
    private FieldBuilder  fieldBuilder;

    @Inject
    public LoginForm(LabelBuilder labelBuilder, FieldBuilder fieldBuilder){
        this.fieldBuilder = fieldBuilder;
        this.labelBuilder = labelBuilder;
        setSizeUndefined();
        setMargin(false);
        Responsive.makeResponsive(this);
        addStyleName("login-panel");

        addComponent(this.labelBuilder);
        addComponent(this.fieldBuilder);
    }


}
