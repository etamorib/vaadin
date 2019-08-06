package de.cas.vaadin.thelibrary.controller;

import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

public interface ControllerInterface<T extends DatabaseBean> {


	boolean add(T bean);
	boolean delete(Set<T> bean);
	ArrayList<T> getItems();
}
