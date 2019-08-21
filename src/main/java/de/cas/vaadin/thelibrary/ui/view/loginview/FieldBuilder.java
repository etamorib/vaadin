package de.cas.vaadin.thelibrary.ui.view.loginview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.event.AppEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.handler.AuthenticationFactory;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;
import de.cas.vaadin.thelibrary.handler.AuthenticationInterface;

public class FieldBuilder extends HorizontalLayout {


    private Provider<Button> buttonProvider;
    private AuthenticationFactory authenticationFactory;
    private Provider<TextField> textFieldProvider;
    private Provider<PasswordField> passwordFieldProvider;
    private TextField textField;
    private PasswordField passwordField;
    private Button signin;

    @Inject
    public FieldBuilder(AuthenticationFactory authenticationFactory, Provider<Button> buttonProvider,
                        Provider<TextField> textFieldProvider, Provider<PasswordField> passwordFieldProvider){
        this.passwordFieldProvider = passwordFieldProvider;
        this.textFieldProvider = textFieldProvider;
        this.buttonProvider = buttonProvider;
        this.authenticationFactory = authenticationFactory;
        textField = this.textFieldProvider.get();
        passwordField = this.passwordFieldProvider.get();
        signin = this.buttonProvider.get();
        addStyleName("fields");
        customizeTextField();
        customizePasswordField();
        customizeButton();
        addComponents(this.textField, this.passwordField, this.signin);
        setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

    }

    private void customizeTextField(){
        textField.setIcon(VaadinIcons.USER);
        textField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        textField.setPlaceholder("Username");
    }
    private void customizePasswordField(){
        passwordField.setIcon(VaadinIcons.LOCK);
        passwordField.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
        passwordField.setPlaceholder("Password");
    }
    private void customizeButton(){
        signin.setCaption("Sign in");
        signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
        signin.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        signin.focus();

        signin.addClickListener(e->{
            //Checking for correct username - password
            AuthenticationInterface authenticationHandler = authenticationFactory.create(textField.getValue(), passwordField.getValue());
            Notification error = authenticationHandler.check();
            //If there was an error, show a notification
            if(error!=null) {

                error.show(Page.getCurrent());
            }
            //If username-password is correct, send a LoginRequestEvent
            AppEventBus.post(new AppEvent.LoginRequestEvent(textField.getValue(), passwordField.getValue(), authenticationHandler));

        });

    }
}
