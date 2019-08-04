package de.cas.vaadin.thelibrary.model;

import java.util.ArrayList;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

public interface DaoInterface<T extends DatabaseBean> {
	boolean update(T bean);
	boolean add(T bean);
	boolean delete(T bean);
	ArrayList<T> getItems();
	static String connectionString() {
		return "jdbc:sqlite:db/";
	}
}
