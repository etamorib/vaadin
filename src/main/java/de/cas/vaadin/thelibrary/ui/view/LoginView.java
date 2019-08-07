package de.cas.vaadin.thelibrary.ui.view;

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
	
	public LoginView() {
	 	setSizeFull();
        setMargin(false);
        setSpacing(false);
        setStyleName("login-background");

        Component loginForm = buildLoginForm();
        addComponent(loginForm);
        setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        /*Notification to show at the bottom
         * It's just a little helper
        */
        Notification notification = new Notification(
                "Welcome to the library app");
        notification
                .setDescription("<span>This is just a trial project. <br> <strong>Username: admin</strong><br>"
                		+ " <strong>Password: admin<br></strong> "
                		+ "</span> <span><b>Sign In</b> button to continue.</span>");
        notification.setHtmlContentAllowed(true);
        notification.setStyleName("closable");
        notification.setPosition(Position.BOTTOM_CENTER);
        notification.setDelayMsec(-1);
        notification.show(Page.getCurrent());
	}
	
	/**
	 * @return A VerticalLayout component with
	 * fields and labels. Basically a login form.
	 */
	private Component buildLoginForm() {
		final VerticalLayout loginPanel = new VerticalLayout();
	    loginPanel.setSizeUndefined();
	    loginPanel.setMargin(false);
	    Responsive.makeResponsive(loginPanel);
	    loginPanel.addStyleName("login-panel");
	
	    loginPanel.addComponent(buildLabels());
	    loginPanel.addComponent(buildFields());
	    return loginPanel;
	}

	/**
	 * @return A HorizontalLayout with styled textfield,
	 * passwordfield and a button. Also sets the button's
	 * ClickListener.
	 */
	private Component buildFields() {
		//Returned layout
		HorizontalLayout fields = new HorizontalLayout();
	    fields.addStyleName("fields");
	
	    //Username field
	    final TextField username = new TextField();
	    //username.setStyleName("text-color-white");
	    username.setIcon(VaadinIcons.USER);
	    username.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	    username.setPlaceholder("Username");
	
	    //Password field
	    final PasswordField password = new PasswordField();
	    //password.setStyleName("text-color-white");
	    password.setIcon(VaadinIcons.LOCK);
	    password.addStyleName(ValoTheme.TEXTFIELD_INLINE_ICON);
	    password.setPlaceholder("Password");
	
	    //Sign in button
	    final Button signin = new Button("Sign In");
	    signin.addStyleName(ValoTheme.BUTTON_PRIMARY);
	    signin.setClickShortcut(KeyCode.ENTER);
	    signin.focus();
	
	    fields.addComponents(username, password, signin);
	    fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);
	    
	    signin.addClickListener(e->{
	    	//Checking for correct username - password
	    	AuthenticationHandler aut = new AuthenticationHandler(username.getValue(), password.getValue());
	    	Notification error = aut.check();
	    	//If there was an error, show a notification
	    	if(error!=null) {
	    		
	    		error.show(Page.getCurrent());
	    	}
	    	//If username-password is correct, send a LoginRequestEvent
	    	AppEventBus.post(new LoginRequestEvent(username.getValue(), password.getValue()));
	    	
	    });
	    return fields;
	}

	/**
	 * @return A CssLayout which contains a styled
	 * welcome label and a styled title label.
	 */
	private Component buildLabels() {
		//Returned layout
		CssLayout labels = new CssLayout();
	    labels.addStyleName("labels");
	    
	    //Welcome label
	    Label welcome = new Label("Welcome");
	    welcome.setSizeUndefined();
	    welcome.addStyleName(ValoTheme.LABEL_H3);
	    welcome.addStyleName(ValoTheme.LABEL_COLORED);
	    labels.addComponent(welcome);
	    
	    //Title label
	    Label title = new Label("Vaadin Library App");
	    title.setSizeUndefined();
	    title.addStyleName(ValoTheme.LABEL_H2);
	    title.addStyleName(ValoTheme.LABEL_LIGHT);
	    title.addStyleName("text-color-white");
	    labels.addComponent(title);
	
	
	    return labels;
	}

}