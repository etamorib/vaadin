package de.cas.vaadin.thelibrary.model;

import de.cas.vaadin.thelibrary.model.bean.DatabaseBean;

/**
 * @author mate.biro
 * Generic interface for extra dao methods
 *
 */
public interface ExtraDaoInterface<T extends DatabaseBean> {
	boolean update(T bean);
	T findById(Integer id);
}
