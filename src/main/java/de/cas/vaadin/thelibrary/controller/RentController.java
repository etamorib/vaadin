package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.RentDAO;
import de.cas.vaadin.thelibrary.model.bean.Rent;

public class RentController implements ControllerInterface<Rent> {
	private RentDAO dao = new RentDAO();

	@Override
	public boolean add(Rent bean) {
		return dao.add(bean);
	}

	@Override
	public boolean delete(Set<Rent> rents) {
		return dao.delete(rents);
	}

	@Override
	public ArrayList<Rent> getItems() {
		return dao.getItems();
	}
	
	public Rent getRentByBookId(Integer id) {
		return dao.getRentByBookId(id);
	}

}
