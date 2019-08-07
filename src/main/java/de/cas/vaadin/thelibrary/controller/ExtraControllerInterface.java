package de.cas.vaadin.thelibrary.controller;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

/**
 * @author mate.biro
 * Interface for controllers which implement
 * extra data manipulating methods
 */
public interface ExtraControllerInterface<T extends DatabaseBean> {
	boolean update(T bean);
	T findById(Integer id);
}
