package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

/**
 * @author mate.biro
 * Generic interface for controllers which
 * must implement basic data manipulating methods
 */
public interface ControllerInterface<T extends DatabaseBean> {

	boolean add(T bean);
	boolean delete(Set<T> bean);
	ArrayList<T> getItems();
	boolean delete(T bean);
}
