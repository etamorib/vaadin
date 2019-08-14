package de.cas.vaadin.thelibrary.model.bean;

/**
 * @author mate.biro
 * Bean class for Book object(s)
 *
 */
public class Book implements DatabaseBean{
	
	//Author maybe new class? For proper name? idk
	private String title, author;
	private Integer Id, year, number;
	private BookState state;
	private Category category;
	public static final String DBname = "book.db";


	public Book(String title, String author, Integer id, Integer year, BookState state, Category category, Integer number) {
		this.title = title;
		this.author = author;
		Id = id;
		this.year = year;
		this.number = number;
		this.state = state;
		this.category = category;
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

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return "ID: "+Id +" - "+ title +" by "+ author +" ("+state+")";
	}




	
	

}
