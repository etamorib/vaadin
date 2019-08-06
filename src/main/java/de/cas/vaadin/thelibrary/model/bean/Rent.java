package de.cas.vaadin.thelibrary.model.bean;

import java.time.LocalDate;

public class Rent implements DatabaseBean {
	
	private LocalDate rentTime, returnTime;
	private Integer bookId, readerId;
	public static final String DBname = "rent.db";
	
	public Rent(LocalDate rentTime, LocalDate returnTime, Integer bookId, Integer readerId) {
		super();
		this.rentTime = rentTime;
		this.returnTime = returnTime;
		this.bookId = bookId;
		this.readerId = readerId;
	}
	
	public Rent() {
		
	}

	public LocalDate getRentTime() {
		return rentTime;
	}

	public void setRentTime(LocalDate rentTime) {
		this.rentTime = rentTime;
	}

	public LocalDate getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(LocalDate returnTime) {
		this.returnTime = returnTime;
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
	
	
	

}
