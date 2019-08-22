package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.model.ReaderDAO;
import de.cas.vaadin.thelibrary.model.bean.Reader;

/**
 * @author mate.biro
 * Controller class for Reader database actions
 *
 */
public class ReaderController implements ControllerInterface<Reader>, ExtraControllerInterface<Reader> {
	private ReaderDAO dao;
	@Inject
	public ReaderController(ReaderDAO dao){
		this.dao = dao;
	}
	
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

	@Override
	public Reader findById(Integer id) {
		return dao.findById(id);
	}

	public boolean delete(Reader r){
		return dao.delete(r);
	}

}
