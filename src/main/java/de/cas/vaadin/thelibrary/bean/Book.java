package de.cas.vaadin.thelibrary.bean;

public class Book {
	
	//Author maybe new class? For proper name? idk
	private String title, author;
	private long Id;
	private int year;
	private BookState state;
	
	public Book(String title, String author, long Id, int year, BookState state ) {
		this.title = title;
		this.author = author;
		this.Id=Id;
		this.year = year;
		this.state = state;
	}
	
	public Book() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public long getId() {
		return Id;
	}

	public void setId(long id) {
		Id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BookState getState() {
		return state;
	}

	public void setState(BookState state) {
		this.state = state;
	}
	
	

}
