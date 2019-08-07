package de.cas.vaadin.thelibrary.model.bean;

import java.time.LocalDate;
/**
 * @author mate.biro
 * Bean class for Waitlist object(s)
 *
 */
public class Waitlist implements DatabaseBean {

	private Integer bookId, readerId;
	private LocalDate waitDate, requestDate;
	public static final String DBname = "waitlist.db";
	
	public Waitlist() {
		
	}

	public Waitlist(Integer bookId, Integer readerId, LocalDate requestDate, LocalDate waitDate) {
		super();
		this.bookId = bookId;
		this.readerId = readerId;
		this.waitDate = waitDate;
		this.requestDate = requestDate;
	}

	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getReaderId() {
		return readerId;
	}

	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}

	public LocalDate getWaitDate() {
		return waitDate;
	}

	public void setWaitDate(LocalDate waitDate) {
		this.waitDate = waitDate;
	}

	public LocalDate getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(LocalDate requestDate) {
		this.requestDate = requestDate;
	}

	public static String getDbname() {
		return DBname;
	}
	
	@Override
	public String toString() {
		return "BOOKID: " + bookId;
	}
	
	
	
	

	
}
