package de.cas.vaadin.thelibrary.ui.view.content.rentview;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.vaadin.data.provider.ListDataProvider;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;
import de.cas.vaadin.thelibrary.controller.MasterController;
import de.cas.vaadin.thelibrary.model.bean.Reader;
import de.cas.vaadin.thelibrary.model.bean.Rent;
import de.cas.vaadin.thelibrary.utils.EmailSender;

import javax.mail.MessagingException;

public class LateGrid extends RentGrid {

    private EmailSender emailSender;

    @Inject
    public LateGrid(MasterController masterController,
                    Provider<Button> buttonProvider,
                    EmailSender emailSender)
    {
        super(masterController, buttonProvider);
        this.emailSender = emailSender;
        setCaption("Late rent(s)");
        addEmailsender();
        setDataProviderImpl();
    }

    @Override
    protected void setDataProviderImpl() {
        super.updateLists();
        ListDataProvider<Rent> listDataProvider = new ListDataProvider<>(super.lateList);
        setDataProvider(listDataProvider);
    }

    private void addEmailsender(){
        addComponentColumn(this::buildSendButton).setCaption("Send email");
    }

    private Button buildSendButton(Rent rent){

		Button sendEmail = buttonProvider.get();
		sendEmail.setIcon(VaadinIcons.MAILBOX);
		sendEmail.setStyleName(ValoTheme.BUTTON_DANGER);
		sendEmail.addClickListener(e->{
			updateLists();

			Reader reader = masterController.getReaderController().findById(rent.getReaderId());
			try {
			    emailSender.send(reader);
			}catch (MessagingException ex) {
			    Notification.show("Sending was unsuccesful!");
			    ex.printStackTrace();
			}
		});
		return sendEmail;
    }
}
