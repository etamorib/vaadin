package de.cas.vaadin.thelibrary.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import de.cas.vaadin.thelibrary.model.bean.Book;
import de.cas.vaadin.thelibrary.model.bean.BookState;
import de.cas.vaadin.thelibrary.model.bean.Category;

/**
 * @author mate.biro
 * This class is responsible to make connection with the database
 * and handle data manipulation in the Book database.
 *
 */

public class BookDAO implements DaoInterface<Book>, ExtraDaoInterface<Book> {
	
	private static final String CONN = DaoInterface.connectionString() + Book.DBname;
	private static final String UPDATE = "UPDATE BOOK SET TITLE=?, AUTHOR=?, YEAR=?, STATE=?, CATEGORY=?, AVAILABLE=? WHERE ID=?";
	private static final String ADD = "INSERT INTO BOOK(ID,TITLE, AUTHOR, YEAR, STATE, CATEGORY, AVAILABLE) VALUES(?,?,?,?,?,?,?)";
	private static final String DEL = "UPDATE BOOK SET STATE = ? WHERE ID=?";
	private static final String LIST = "SELECT * FROM BOOK";
	private static final String FIND = "SELECT * FROM BOOK WHERE ID=?";
	private static final String DEL_BOOK = "UPDATE BOOK SET STATE =? WHERE ID=?";

	public BookDAO() {
		try {
			Class.forName("org.sqlite.JDBC");
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates a row in the Book database
	 * @return true is update was succesful <br>
	 * false if update was unsuccessful
	 * @param Book object
	 * 
	 */
	@Override
	public boolean update(Book bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
			PreparedStatement pst = conn.prepareStatement(UPDATE)	
				){
			pst.setString(1, bean.getTitle());
			pst.setString(2, bean.getAuthor());
			pst.setInt(3, bean.getYear());
			pst.setString(4, bean.getState().toString());
			pst.setString(5,bean.getCategory().toString());
			pst.setInt(6, bean.getNumber());
			pst.setInt(7, bean.getId());
			
			int affected = pst.executeUpdate();
			if(affected!=1) {
				return false;
			}else {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/** 
	 * Adds a new book the the Book database.
	 * @return true if adding was successful <br>
	 * false it adding was unsuccessful
	 * @param Book object
	 */
	@Override
	public boolean add(Book bean) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(ADD)	
					){
				pst.setInt(1, bean.getId());
				pst.setString(2, bean.getTitle());
				pst.setString(3, bean.getAuthor());
				pst.setInt(4, bean.getYear());
				pst.setString(5, bean.getState().toString());
				pst.setString(6,bean.getCategory().toString());
				pst.setInt(7, bean.getNumber());
				
				int affected = pst.executeUpdate();
				if(affected!=1) {
					return false;
				}else {
					return true;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
	}

	/**
	 * Deletes one or more row from the Book database.
	 * @return true if deleting was successful <br>
	 * false if deleting was unsuccessful
	 * @param A set of Book objects
	 */
	@Override
	public boolean delete(Set<Book> books) {
		try(Connection conn = DriverManager.getConnection(CONN);
				PreparedStatement pst = conn.prepareStatement(DEL)
				){
				for(Book bean : books) {
//					System.out.println(bean);
					pst.setString(1, BookState.Deleted.toString());
					pst.setInt(2, bean.getId());
					
					
					int affected = pst.executeUpdate();
					if(affected!=1) {
						return false;
					}
				}
				return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(Book book) {
		try(Connection conn = DriverManager.getConnection(CONN);
			PreparedStatement pst = conn.prepareStatement(DEL_BOOK)
		){

			pst.setString(1, BookState.Deleted.toString());
			pst.setInt(2, book.getId());


			int affected = pst.executeUpdate();
			if(affected!=1) {
				return false;
			}

			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}
	/**
	 * Return every data from the Book database
	 * @return ArrayList of Book objects
	 */
	@Override
	public ArrayList<Book> getItems() {
		ArrayList<Book> result = new ArrayList<>();
		try(Connection conn = DriverManager.getConnection(CONN);
			Statement st = conn.createStatement()
				){
			ResultSet rs = st.executeQuery(LIST);
			while(rs.next()) {
				Book b = new Book(rs.getString(2), rs.getString(3), rs.getInt(1),
						rs.getInt(4), BookState.valueOf(rs.getString(5)), Category.valueOf(rs.getString(7)), rs.getInt(6));
				result.add(b);
			}
			return result;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Finds a book data of the given id
	 * @return The Book object with the given id
	 * @param The id of the book
	 */
	@Override
	public Book findById(Integer id) {
		Book b = null;
		try(Connection conn = DriverManager.getConnection(CONN);
			PreparedStatement pst = conn.prepareStatement(FIND)
				){
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				b = new Book(rs.getString(2), rs.getString(3), rs.getInt(1),
						rs.getInt(4), BookState.valueOf(rs.getString(5)),
						Category.valueOf(rs.getString(7)), rs.getInt(6));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
		
	}
	


	
	
}
