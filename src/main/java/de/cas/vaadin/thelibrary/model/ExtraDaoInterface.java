package de.cas.vaadin.thelibrary.model;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

public interface ExtraDaoInterface<T extends DatabaseBean> {
	boolean update(T bean);
	T findById(Integer id);
}
