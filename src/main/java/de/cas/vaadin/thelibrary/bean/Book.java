package de.cas.vaadin.thelibrary.bean;

/**
 * @author mate.biro
 * Bean class for Book object(s)
 *
 */
public class Book {
	
	//Author maybe new class? For proper name? idk
	private String title, author;
	private Long Id;
	private Integer year;
	private BookState state;
	
	public Book(String title, String author, Long Id, Integer year, BookState state ) {
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BookState getState() {
		return state;
	}

	public void setState(BookState state) {
		this.state = state;
	}
	
	

}
