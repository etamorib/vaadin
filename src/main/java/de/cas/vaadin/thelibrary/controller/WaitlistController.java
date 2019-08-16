package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.model.WaitlistDAO;
import de.cas.vaadin.thelibrary.model.bean.Waitlist;

/**
 * @author mate.biro
 * Controller class for Waitlist database actions
 *
 */
public class WaitlistController implements ControllerInterface<Waitlist> {
	
	private WaitlistDAO dao;
	@Inject
	public WaitlistController(WaitlistDAO dao){
		this.dao = dao;
	}

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
