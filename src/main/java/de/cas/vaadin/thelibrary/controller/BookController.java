package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;

import de.cas.vaadin.thelibrary.model.BookDAO;
import de.cas.vaadin.thelibrary.model.bean.Book;

public class BookController implements ControllerInterface<Book>  {
	
	private BookDAO bookDAO = new BookDAO();

	@Override
	public boolean update(Book bean) {
		
		return bookDAO.add(bean);
	}

	@Override
	public boolean add(Book bean) {
		return bookDAO.add(bean);
	}

	@Override
	public boolean delete(Book bean) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Book> getItems() {
		return bookDAO.getItems();
	}

}
