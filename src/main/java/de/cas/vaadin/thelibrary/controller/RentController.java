package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.model.RentDAO;
import de.cas.vaadin.thelibrary.model.bean.Rent;

/**
 * @author mate.biro
 * Controller class for Rent database actions
 *
 */
public class RentController implements ControllerInterface<Rent> {
	private RentDAO dao;
	@Inject
	public RentController(RentDAO dao){
		this.dao = dao;
	}

	@Override
	public boolean add(Rent bean) {
		return dao.add(bean);
	}

	@Override
	public boolean delete(Set<Rent> rents) {
		return dao.delete(rents);
	}

	@Override
	public boolean delete(Rent rent){
		return dao.delete(rent);
	}

	@Override
	public ArrayList<Rent> getItems() {
		return dao.getItems();
	}


	public Rent getRentByBookId(Integer id) {
		return dao.getRentByBookId(id);
	}

}
