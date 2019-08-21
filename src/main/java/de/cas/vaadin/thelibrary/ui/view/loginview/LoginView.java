package de.cas.vaadin.thelibrary.ui.view.loginview;

import com.google.inject.Inject;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.shared.Position;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.cas.vaadin.thelibrary.event.AppEvent.LoginRequestEvent;
import de.cas.vaadin.thelibrary.event.AppEventBus;
import de.cas.vaadin.thelibrary.handler.AuthenticationHandler;

/**
 * @author mate.biro
 * A Component container class.
 * Responsible for showing the view of the Login page.
 */
@SuppressWarnings("serial")
public class LoginView extends VerticalLayout {

	private LoginForm loginForm;

	@Inject
	public LoginView(LoginForm loginForm) {
		this.loginForm = loginForm;
	 	setSizeFull();
        setMargin(false);
        setSpacing(false);
        setStyleName("login-background");

       //Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

	}

}