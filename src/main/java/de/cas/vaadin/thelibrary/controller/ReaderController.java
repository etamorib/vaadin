package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.ReaderDAO;
import de.cas.vaadin.thelibrary.model.bean.Reader;

public class ReaderController implements ControllerInterface<Reader> {

	private ReaderDAO dao = new ReaderDAO();
	
	@Override
	public boolean update(Reader bean) {
		return dao.update(bean);
	}

	@Override
	public boolean add(Reader bean) {
		return dao.add(bean);
	}

	@Override
	public boolean delete(Set<Reader> readers) {
		return dao.delete(readers);
	}

	@Override
	public ArrayList<Reader> getItems() {
		return dao.getItems();
	}

}
