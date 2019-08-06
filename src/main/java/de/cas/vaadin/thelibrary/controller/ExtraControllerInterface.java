package de.cas.vaadin.thelibrary.controller;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

public interface ExtraControllerInterface<T extends DatabaseBean> {
	boolean update(T bean);
	T findById(Integer id);
}
