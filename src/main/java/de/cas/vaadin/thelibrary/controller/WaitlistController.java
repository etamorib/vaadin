package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.WaitlistDAO;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;

public class WaitlistController implements ControllerInterface<Waitlist> {
	
	private final WaitlistDAO dao = new WaitlistDAO();

	@Override
	public boolean add(Waitlist bean) {
		return dao.add(bean);
	}

	@Override
	public boolean delete(Set<Waitlist> waitlists) {
		return dao.delete(waitlists);
	}

	@Override
	public ArrayList<Waitlist> getItems() {
		return dao.getItems();
	}
	
	

}
