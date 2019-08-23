package de.cas.vaadin.thelibrary.model;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

/**
 * @author mate.biro
 * Generic interface for basic dao methods
 *
 */
public interface DaoInterface<T extends DatabaseBean> {
	boolean add(T bean);
	boolean delete(Set<T> bean);
	boolean delete(T bean);
	ArrayList<T> getItems();
	static String connectionString() {
		return "jdbc:sqlite:db/";
	}

}
