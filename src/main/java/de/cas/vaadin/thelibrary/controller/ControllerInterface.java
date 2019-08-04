package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

public interface ControllerInterface<T extends DatabaseBean> {

	boolean update(T bean);
	boolean add(T bean);
	boolean delete(T bean);
	ArrayList<T> getItems();
}
