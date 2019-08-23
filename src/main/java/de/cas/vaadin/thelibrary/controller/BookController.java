package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import com.google.inject.Inject;
import de.cas.vaadin.thelibrary.model.BookDAO;
import de.cas.vaadin.thelibrary.model.bean.Book;

/**
 * @author mate.biro
 * Controller class for Book database actions
 *
 */
public class BookController implements ControllerInterface<Book>, ExtraControllerInterface<Book>  {
	
	private BookDAO bookDAO = new BookDAO();
	@Inject
	public BookController(BookDAO bookDAO){
		this.bookDAO = bookDAO;
	}

	@Override
	public boolean update(Book bean) {
		return bookDAO.update(bean);
	}

	@Override
	public boolean add(Book bean) {
		return bookDAO.add(bean);
	}

	@Override
	public boolean delete(Set<Book> beans) {
		return bookDAO.delete(beans);
	}

	@Override
	public ArrayList<Book> getItems() {
		return bookDAO.getItems();
	}

	@Override
	public Book findById(Integer id) {
		return bookDAO.findById(id);
	}

	@Override
	public boolean delete(Book b){
		return bookDAO.delete(b);
	}

}
